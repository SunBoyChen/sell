package com.zhihua.sell.service.impl;

import com.zhihua.sell.dao.ProductCategoryDao;
import com.zhihua.sell.pojo.ProductCategory;
import com.zhihua.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryDao categoryDao;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return categoryDao.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDao.save(productCategory);
    }
}
