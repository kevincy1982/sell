package com.sell.sell.services.impl;

import com.sell.sell.converter.OrderMaster2OrderDTOConverter;
import com.sell.sell.dataobject.OrderDetail;
import com.sell.sell.dataobject.OrderMaster;
import com.sell.sell.dataobject.ProductInfo;
import com.sell.sell.dto.CartDTO;
import com.sell.sell.dto.OrderDTO;
import com.sell.sell.enums.OrderStatusEnum;
import com.sell.sell.enums.PayStatusEnum;
import com.sell.sell.enums.ResultEnum;
import com.sell.sell.exception.SellException;
import com.sell.sell.repository.OrderDetailRepository;
import com.sell.sell.repository.OrderMasterRepository;
import com.sell.sell.services.OrderService;
import com.sell.sell.services.ProductService;
import com.sell.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        //1. check product : quantity and price
        for(OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT__EXIST);
            }
            //2. calculate total price
            orderAmount = productInfo.getProductPrice()
                          .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                          .add(orderAmount);
            //write order into db
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        //3. write into db, orderMaster and OrderDetail
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAYED.getCode());
        orderMasterRepository.save(orderMaster);
        //4. recalculate stock
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                                            .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                                            .collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOS = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return  new PageImpl<OrderDTO>(orderDTOS,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        // check order status
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
             log.error("[Cancel order], wrong order status, orderId={}, orderStatus={}",orderDTO.getOrderId(), orderDTO.getOrderStatus());
             throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // change order status
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("[Cancel order], Failed to cancel order, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //restore stock
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[Cancel order], no product in the oredr. orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_WITH_NO_PRODUCT);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                                            .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                                            .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //refund if paied
        if(orderDTO.getPayStatus().equals(PayStatusEnum.PAYED.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // check order status
        if(!orderDTO.getPayStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[Finished order], wrong order status, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // change status
        orderDTO.setPayStatus(PayStatusEnum.PAYED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[Finished order], Failed to finish order, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // check order status
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[Paied order], wrong order status, orderId={}, orderStatus={}",orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // check pay status
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[Paied order], wrong pay status for the order, orderId={} ", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_WITH_WRONG_PAY_STATUS);
        }
        // change pay status
        orderDTO.setPayStatus(PayStatusEnum.PAYED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[Paied order], Failed to pay order, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_PAY_ERROR);
        }
        return orderDTO;
    }
}
