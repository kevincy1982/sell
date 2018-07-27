package com.sell.sell.services.impl;

import com.sell.sell.dataobject.ProductInfo;
import com.sell.sell.dto.CartDTO;
import com.sell.sell.enums.ProductStatusEnum;
import com.sell.sell.enums.ResultEnum;
import com.sell.sell.exception.SellException;
import com.sell.sell.repository.ProductInfoRepository;
import com.sell.sell.services.CategoryService;
import com.sell.sell.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
       for(CartDTO cartDTO : cartDTOList){
           ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
           if(productInfo==null){
               throw  new SellException(ResultEnum.PRODUCT_NOT__EXIST);
           }
           Integer newQuantity = productInfo.getProductStock() + cartDTO.getProductQuantity();
           productInfo.setProductStock(newQuantity);
           productInfoRepository.save(productInfo);
       }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
         for(CartDTO cartDTO : cartDTOList){
             ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
             if(productInfo == null){
                 throw new SellException((ResultEnum.PRODUCT_NOT__EXIST));
             }
             Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
             if(result < 0){
                 throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
             }

             productInfo.setProductStock(result);
             productInfoRepository.save(productInfo);
         }
    }

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
