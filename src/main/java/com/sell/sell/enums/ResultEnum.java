package com.sell.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT__EXIST(10, "No such product"),
    PRODUCT_STOCK_ERROR(11, "Wrong stock"),
    ORDER_NOT_EXIST(12, "Order not exist"),
    ORDERDETAIL_NOT_EXIST(13, "Order detail not exist"),
    ORDER_STATUS_ERROR(14, "Wrong order status"),
    ORDER_UPDATE_FAIL(15, "Fail to update order"),
    ORDER_WITH_NO_PRODUCT(16, "No product in the order"),
    ORDER_WITH_WRONG_PAY_STATUS(17, "order with wrong pay status"),
    ORDER_PAY_ERROR(18, "order pay error"),
    WRONG_ARGUMENTS(1,"arguments are wrong"),
    CART_EMPTY(19, "cart is empty"),
    OPENID_EMPTY(20, "open id is empty"),
    OPENID_NOT_MATCHED(21, "openid is not matched")
    ;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;
}
