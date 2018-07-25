package com.sell.sell.services;

import com.sell.sell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    ProductInfo findOne(String id);

    // find all produc in stock
    List<ProductInfo> findUpALl();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //increase stock

    //decrease stock
}