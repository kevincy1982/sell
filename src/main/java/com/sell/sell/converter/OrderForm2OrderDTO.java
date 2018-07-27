package com.sell.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sell.sell.dataobject.OrderDetail;
import com.sell.sell.dto.OrderDTO;
import com.sell.sell.enums.ResultEnum;
import com.sell.sell.exception.SellException;
import com.sell.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTO {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone((orderForm.getPhone()));
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),
                          new TypeToken<List<OrderDetail>>(){}.
                                  getType());
        } catch (Exception e){
            log.error("[Object Convert] error, Sting={}",orderForm.getItems());
            throw new SellException(ResultEnum.WRONG_ARGUMENTS);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;

    }
}
