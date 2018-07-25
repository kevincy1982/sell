package com.sell.sell.VO;

import lombok.Data;

import java.util.List;

@Data
public class ResultVO<T> {

    /*status code*/
    private Integer code;
    /*desv*/
    private String msg;
    // return data
    private T data;
}
