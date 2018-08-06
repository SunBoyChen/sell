package com.zhihua.sell.dao;

import com.zhihua.sell.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoDao extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);
}
