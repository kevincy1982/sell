package com.sell.sell.services.impl;

import com.sell.sell.dataobject.ProductInfo;
import com.sell.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo result = productService.findOne("123456");
        Assert.assertEquals("123456", result.getProductId());
    }

    @Test
    public void findUpALl() {
        List<ProductInfo> resultList = productService.findUpALl();
        Assert.assertEquals(1,resultList.size());

    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> resultList = productService.findAll(request);
        //System.out.println(resultList.getTotalElements());
        Assert.assertNotEquals(0,resultList.getTotalPages());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234567");
        productInfo.setProductName("pizza");
        productInfo.setProductPrice(new BigDecimal((3.2)));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("tasty");
        productInfo.setProductIcon("http://xxx.com.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}