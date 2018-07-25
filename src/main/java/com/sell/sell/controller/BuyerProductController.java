package com.sell.sell.controller;

import com.sell.sell.VO.ProductInfoVO;
import com.sell.sell.VO.ProductVO;
import com.sell.sell.VO.ResultVO;
import com.sell.sell.dataobject.ProductCategory;
import com.sell.sell.dataobject.ProductInfo;
import com.sell.sell.services.CategoryService;
import com.sell.sell.services.ProductService;
import com.sell.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1. list all product in stock
        List<ProductInfo> productInfoList = productService.findUpALl();
        //2. check for category (check for all once)
        List<Integer> categoryTypeList = productInfoList.stream()
                                         .map(e -> e.getCategoryType())
                                         .collect(Collectors.toList());

        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);
        //3/ data into json format
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategories){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        return ResultVOUtil.success(productVOList);
    }
}
