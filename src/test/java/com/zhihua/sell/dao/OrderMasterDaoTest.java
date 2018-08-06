package com.zhihua.sell.dao;

import com.zhihua.sell.enums.OrderStatusEnums;
import com.zhihua.sell.enums.PayStatusEnums;
import com.zhihua.sell.pojo.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {


    @Autowired
    private OrderMasterDao dao;

    private final String OPENID = "110110";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("志華");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("黑馬");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        OrderMaster result = dao.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() throws Exception {

        Pageable pageable = new PageRequest(0,1);
        Page<OrderMaster> page = dao.findByBuyerOpenid(OPENID, pageable);

        Assert.assertNotEquals(0,page.getTotalElements());
    }
}