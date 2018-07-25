package com.sell.sell.enums;

import lombok.Getter;

@Getter
public enum  PayStatusEnum {
    PAYED(1, "payed"),
    UNPAYED(0,"unpaied")
    ;

    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
