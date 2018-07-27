package com.sell.sell.repository;

import com.sell.sell.dataobject.OrderDetail;
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
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("234");
        orderDetail.setOrderId("123467");
        orderDetail.setProductIcon("xxx.jpg");
        orderDetail.setProductId("110");
        orderDetail.setProductName("banana");
        orderDetail.setProductPrice(new BigDecimal(9.1));
        orderDetail.setProductQuantity(100);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setDetailId("465");
        orderDetail1.setOrderId("3456");
        orderDetail1.setProductIcon("xxx.jpg");
        orderDetail1.setProductId("111");
        orderDetail1.setProductName("apple");
        orderDetail1.setProductPrice(new BigDecimal(9.1));
        orderDetail1.setProductQuantity(100);

        OrderDetail result = orderDetailRepository.save(orderDetail);
        OrderDetail result1 = orderDetailRepository.save(orderDetail1);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result1);

    }
    @Test
    public void findByOrOrderId() {
        String orderId = "3456";
        List<OrderDetail> result = orderDetailRepository.findByOrderId(orderId);
        Assert.assertNotEquals(0,result.size());

    }
}