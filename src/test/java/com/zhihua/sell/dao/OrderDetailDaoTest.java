package com.zhihua.sell.dao;

import com.zhihua.sell.pojo.OrderDetail;
import com.zhihua.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void saveTest() {
        OrderDetail detail = new OrderDetail();

        detail.setDetailId(KeyUtil.genUniqueKey());
        detail.setOrderId(KeyUtil.genUniqueKey());
        detail.setProductId("123456");
        detail.setProductName("皮皮虾");
        detail.setProductPrice(new BigDecimal(15.5));
        detail.setProductIcon("http://xxxx.jpg");
        detail.setProductQuantity(2);
        OrderDetail result = orderDetailDao.save(detail);

        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> byOrderId = orderDetailDao.findByOrderId("1531988132437665091");
        Assert.assertNotEquals(0,byOrderId.size());
    }

}