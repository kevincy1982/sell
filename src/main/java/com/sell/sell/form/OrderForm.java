package com.sell.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {
    @NotEmpty(message = "buyer's name can not be empty")
    private String name;

    @NotEmpty(message = "buyer's phone can not be empty")
    private String phone;

    @NotEmpty(message = "buyer's address can not be empty")
    private String address;

    @NotEmpty(message = "buyer's openid can not be empty")
    private String openid;

    @NotEmpty(message = "cart can not be empty")
    private String items;
}
