package com.sell.sell.services;

import com.sell.sell.dto.OrderDTO;

public interface BuyerService {
    // query one order
    OrderDTO findOrderOne(String openid, String orderId);

    // cancel one order
    OrderDTO cancelOneOrder(String openid, String orderId);
}
