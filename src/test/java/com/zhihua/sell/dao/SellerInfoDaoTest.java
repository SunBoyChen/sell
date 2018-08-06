package com.zhihua.sell.dao;

import com.zhihua.sell.pojo.SellerInfo;
import com.zhihua.sell.utils.KeyUtil;
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
public class SellerInfoDaoTest {

    @Autowired
    private SellerInfoDao dao;

    @Test
    public void save() {
        SellerInfo seller = new SellerInfo();
        seller.setOpenid("123456");
        seller.setPassword("123456");
        seller.setSellerId(KeyUtil.genUniqueKey());
        seller.setUsername("zs");
        SellerInfo sellerInfo = dao.save(seller);
        assertNotNull(seller);
    }

    @Test
    public void findByOpenid() {
        SellerInfo seller = dao.findByOpenid("123456");
        assertNotNull(seller);
    }
}