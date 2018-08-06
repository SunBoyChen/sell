package com.zhihua.sell.service.impl;

import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.enums.OrderStatusEnums;
import com.zhihua.sell.enums.PayStatusEnums;
import com.zhihua.sell.pojo.OrderDetail;
import com.zhihua.sell.service.OrderService;
import com.zhihua.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private String orderId = KeyUtil.genUniqueKey();

    @Test
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderDTO.setBuyerName("志华");
        orderDTO.setBuyerAddress("中粮");
        orderDTO.setBuyerOpenid("123456789");
        orderDTO.setBuyerPhone("88888888");

        List<OrderDetail> detailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(10);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(10);
        
        detailList.add(o1);
        detailList.add(o2);
        orderDTO.setOrderDetailList(detailList);

        OrderDTO result = orderService.create(orderDTO);
        //log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);

    }

    @Test
    public void findOne() {

        OrderDTO result = orderService.findOne("1532052634972549338");

        Assert.assertNotNull(result);
    }

    @Test
    public void findList() {

        Pageable pageable = new PageRequest(0,1);
        Page<OrderDTO> page = orderService.findList("123456789", pageable);
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1532052634972549338");
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(), result.getOrderStatus());

    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1532052634972549338");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnums.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1532052634972549338");
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnums.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void findList1() {

        Pageable pageable = new PageRequest(0,10);
        Page<OrderDTO> page = orderService.findList(pageable);
        Assert.assertNotEquals(0,page.getTotalElements());
    }
}