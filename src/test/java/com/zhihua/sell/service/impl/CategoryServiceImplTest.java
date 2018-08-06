package com.zhihua.sell.service.impl;

import com.zhihua.sell.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(1);
        System.out.println(category);
        Assert.assertEquals(new Integer(1),category.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> list = categoryService.findAll();
        list.forEach(System.out::println);
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        list.forEach(System.out::println);
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory category = new ProductCategory("男士用品", 4);
        ProductCategory p = categoryService.save(category);
        Assert.assertEquals(new Integer(4), p.getCategoryType());
    }
}