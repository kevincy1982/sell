package com.sell.sell.repository;

import com.sell.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("kfc");
        productInfo.setProductPrice(new BigDecimal((3.2)));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("tasty");
        productInfo.setProductIcon("http://xxx.com.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);


    }

    @Test
    public void findByProductStatus() {

        List<ProductInfo> pList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,((List) pList).size());
    }
}