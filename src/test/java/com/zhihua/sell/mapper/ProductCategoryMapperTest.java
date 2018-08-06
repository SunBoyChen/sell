package com.zhihua.sell.mapper;

import com.zhihua.sell.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper ProductCategoryMapper;

    @Test
    public void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name","华哥最爱");
        map.put("category_type",5);
        Integer integer = ProductCategoryMapper.insertByMap(map);

        Assert.assertEquals(integer,new Integer(1));
    }

    @Test
    public void insertByObject() {

        ProductCategory category = new ProductCategory();
        category.setCategoryName("水果");
        category.setCategoryType(8);
        Integer integer = ProductCategoryMapper.insertByObject(category);
        Assert.assertEquals(integer,new Integer(1));
    }


    @Test
    public void findByCategoryType(){
        ProductCategory category = ProductCategoryMapper.findByCategoryType(8);
        Assert.assertNotNull(category);
    }


    @Test
    public void findByCategoryName(){
        List<ProductCategory> list = ProductCategoryMapper.findByCategoryName("食物");
        Assert.assertNotEquals(list.size(),0);
    }

    @Test
    public void UpdateByCategoryType(){
        Integer integer = ProductCategoryMapper.updateByCategoryType("水果", 8);
        Assert.assertEquals(integer,new Integer(1));
    }


    @Test
    public void deleteByCategoryType(){
        Integer integer = ProductCategoryMapper.deleteByCategoryType(8);
        Assert.assertEquals(integer,new Integer(1));
    }


    public static void main(String[] args) {
        System.out.println("1234");
    }


    @Test
    public void selectByCategoryType(){
        ProductCategory category = ProductCategoryMapper.selectByCategoryType(8);
        Assert.assertNotNull(category);
    }

}