package com.sell.sell.services.impl;

import com.sell.sell.dto.OrderDTO;
import com.sell.sell.enums.ResultEnum;
import com.sell.sell.exception.SellException;
import com.sell.sell.services.BuyerService;
import com.sell.sell.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOneOrder(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            log.error("[Cancel Order] Order can not be found. orderId={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[Query Order] openid not matched. openid ={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.OPENID_NOT_MATCHED);

        }
        OrderDTO cancelResult = orderService.cancel(orderDTO);
        return cancelResult;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[Query Order] openid not matched. openid ={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.OPENID_NOT_MATCHED);

        }
        return orderDTO;
    }
}
