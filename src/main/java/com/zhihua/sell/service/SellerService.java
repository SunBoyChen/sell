package com.zhihua.sell.service;

import com.zhihua.sell.pojo.SellerInfo;

public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
