package com.zhihua.sell.dao;

import com.zhihua.sell.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void findOneTest(){
        ProductCategory one = productCategoryDao.findOne(1);
        System.out.println(one);
        Assert.assertNotNull(one);
    }



    @Test
    public void saveTest(){
        ProductCategory productCategory = productCategoryDao.findOne(1);
        //productCategory.setCategoryName("婴儿用品");
        productCategory.setCategoryType(2);
        ProductCategory result = productCategoryDao.save(productCategory);

        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<ProductCategory> list = productCategoryDao.findByCategoryTypeIn(Arrays.asList(1, 2));
        list.forEach(System.out::println);
        Assert.assertNotEquals(0, list.size());
    }

}