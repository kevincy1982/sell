package com.sell.sell.converter;

import com.sell.sell.dataobject.OrderMaster;
import com.sell.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

    public static  OrderDTO converter(OrderMaster orderMaster ){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map( e-> converter(e)).collect(Collectors.toList());
    }
}
