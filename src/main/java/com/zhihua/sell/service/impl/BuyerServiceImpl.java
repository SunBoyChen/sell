package com.zhihua.sell.service.impl;

import com.zhihua.sell.dao.OrderMasterDao;
import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.pojo.OrderMaster;
import com.zhihua.sell.service.BuyerService;
import com.zhihua.sell.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {

    private Logger log = LoggerFactory.getLogger(BuyerServiceImpl.class);

    @Autowired
    private OrderService OrderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return this.checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        OrderService.cancel(orderDTO);

        return  orderDTO;
    }


    //检查是否是自己的订单
    private OrderDTO checkOrderOwner(String openid, String orderId){

        OrderDTO orderDTO = OrderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }


        if(!openid.equals(orderDTO.getBuyerOpenid())){
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return  orderDTO;
    }




}
