package com.zhihua.sell.service.impl;

import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.pojo.OrderMaster;
import com.zhihua.sell.service.OrderService;
import com.zhihua.sell.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class payServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {

        OrderDTO orderDTO = orderService.findOne("1532315203997718734");
        payService.create(orderDTO);
    }


    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("1532315203997718734");
        payService.refund(orderDTO);
    }
}