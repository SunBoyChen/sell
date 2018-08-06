package com.zhihua.sell.service.impl;

import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.service.OrderService;
import com.zhihua.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() {

        OrderDTO orderDTO = orderService.findOne("1532315203997718734");

        pushMessageService.orderStatus(orderDTO);

    }
}