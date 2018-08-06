package com.zhihua.sell.service.impl;

import com.zhihua.sell.dao.ProductInfoDao;
import com.zhihua.sell.enums.ProductStatusEnums;
import com.zhihua.sell.pojo.ProductInfo;
import com.zhihua.sell.service.ProductService;
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
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findOne() {
        ProductInfo product = productService.findOne("123456");
        System.out.println(product);
        Assert.assertNotNull(product);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> list = productService.findUpAll();
        list.forEach(System.out::println);
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(0,2);
        Page<ProductInfo> all = productService.findAll(pageable);
        Assert.assertNotEquals(0,all.getTotalElements());

    }

    @Test
    public void save() {
        ProductInfo product = new ProductInfo();
        product.setProductId("123458");
        product.setProductName("尿不湿");
        product.setProductPrice(new BigDecimal(22.5));
        product.setProductDescription("还不错");
        product.setCategoryType(2);
        product.setProductStatus(ProductStatusEnums.UP.getCode());
        product.setProductIcon("http://xxxxx.jpg");
        product.setProductStock(100);

        ProductInfo result = productService.save(product);
        Assert.assertNotNull(result);
    }


    @Test
    public void onSale() {
        ProductInfo productInfo = productService.onSale("123457");
        Assert.assertEquals(productInfo.getProductStatus(),ProductStatusEnums.UP.getCode());

    }


    @Test
    public void offSale() {
        ProductInfo productInfo = productService.offSale("123457");
        Assert.assertEquals(productInfo.getProductStatus(),ProductStatusEnums.DOWN.getCode());


    }

}