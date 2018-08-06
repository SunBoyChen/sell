package com.zhihua.sell.dao;

import com.zhihua.sell.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {

    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
