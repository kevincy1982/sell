package com.sell.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductVO {
    @JsonProperty("name") // place to define the json property name as name
    private String categoryName; // more specific name, so later on the name of the variable will not be confused.

    @JsonProperty("type")  // place to define the json property name as typr
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
