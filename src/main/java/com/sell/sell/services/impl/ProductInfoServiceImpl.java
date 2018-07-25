package com.sell.sell.services.impl;

import com.sell.sell.dataobject.ProductInfo;
import com.sell.sell.enums.ProductStatusEnum;
import com.sell.sell.repository.ProductInfoRepository;
import com.sell.sell.services.CategoryService;
import com.sell.sell.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;


    @Override
    public ProductInfo findOne(String id) {
        return productInfoRepository.findOne(id);
    }

    @Override
    public List<ProductInfo> findUpALl() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }
}
