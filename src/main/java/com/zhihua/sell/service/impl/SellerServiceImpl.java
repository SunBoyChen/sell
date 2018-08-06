package com.zhihua.sell.service.impl;

import com.zhihua.sell.dao.SellerInfoDao;
import com.zhihua.sell.pojo.SellerInfo;
import com.zhihua.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }
}
