package com.zhihua.sell.service;


import com.zhihua.sell.dto.OrderDTO;

public interface BuyerService {

    OrderDTO findOrderOne(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
