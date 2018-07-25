package com.sell.sell.repository;

import com.sell.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    private  final String OPENID = "110";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("9881");
        orderMaster.setBuyerName("father");
        orderMaster.setBuyerPhone("6478896189");
        orderMaster.setBuyerAddress("20 orc dr");
        orderMaster.setBuyerOpenid("110");
        orderMaster.setOrderAmount(new BigDecimal(2.4));

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);

    }
    @Test
    public void findByBuyerOpenId() {
        PageRequest request = new PageRequest(1,2);
        Page<OrderMaster> resullt = orderMasterRepository.findByBuyerOpenid(OPENID, request);
        System.out.println("number ele: " + resullt.getTotalElements());
        Assert.assertNotEquals(0, resullt.getTotalElements());
        System.out.println("number page: " + resullt.getTotalPages());
    }
}