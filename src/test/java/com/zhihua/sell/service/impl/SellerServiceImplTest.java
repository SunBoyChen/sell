package com.zhihua.sell.service.impl;

import com.zhihua.sell.pojo.SellerInfo;
import com.zhihua.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo seller = sellerService.findSellerInfoByOpenid("123456");

        Assert.assertNotNull(seller);

    }
}