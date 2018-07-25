package com.sell.sell.services.impl;

import com.sell.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;
    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> pList = categoryService.findAll();
        Assert.assertEquals(new Integer(4), new Integer(pList.size()));
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertEquals(new Integer(3), new Integer(productCategoryList.size()));
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("boys only", 10);
        ProductCategory productCategory1 = categoryService.save(productCategory);
        Assert.assertNotEquals(null,productCategory);
    }
}