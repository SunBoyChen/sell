package com.zhihua.sell.dao;

import com.zhihua.sell.pojo.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {

    public List<ProductInfo> findByProductStatus(Integer productStatus);
}
