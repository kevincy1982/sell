package com.sell.sell.repository;

import com.sell.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
   @Autowired
    private ProductCategoryRepository repository;

   @Test
    public void findOneTest(){
       ProductCategory pc = repository.findOne(1);
       System.out.println(pc.toString());
   }

   @Test
   @Transactional // rollback the sql operation
    public void saveTest(){
       ProductCategory pc = new ProductCategory("girl fav",3);
       ProductCategory result = repository.save(pc);
       Assert.assertNotNull(result);
       Assert.assertNotEquals(null, result);
   }

   @Test
    public void updateTest(){
       ProductCategory pc = repository.findOne(2);
       pc.setCategoryType(12);
       repository.save(pc);
   }

   @Test
    public void findByCategoryTypeInTest(){
       List<Integer> list = Arrays.asList(2,3,4);
       List<ProductCategory> pList = repository.findByCategoryTypeIn(list);
       Assert.assertNotEquals(0,pList.size());
   }
}