package com.sell.sell.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {

    /*status code*/
    private Integer code;
    /*desv*/
    private String msg;
    // return data
    private T data;
}
