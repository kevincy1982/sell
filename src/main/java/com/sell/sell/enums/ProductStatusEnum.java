package com.sell.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {

    UP(0, "in stock"),
    DOWN(1, "sold out")
    ;
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code , String message){
        this.code = code;
        this.message = message;
    }

}
