package com.zhihua.sell.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.zhihua.sell.dto.OrderDTO;

import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.service.OrderService;
import com.zhihua.sell.service.PayService;
import com.zhihua.sell.utils.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    private static final Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private BestPayService BestPayService;


    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest request = new PayRequest();
        request.setOpenid(orderDTO.getBuyerOpenid());
        request.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        request.setOrderId(orderDTO.getOrderId());
        request.setOrderName(ORDER_NAME);
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付, request={}", JSON.toJSON(request));
        PayResponse response = BestPayService.pay(request);
        log.info("【微信支付】发起支付, response={}", JSON.toJSON(response));

        return  response;
    }

    @Override
    public PayResponse notify(String notifyData) {

        PayResponse response = BestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知, request={}", JSON.toJSON(response));

        //查询出订单
        OrderDTO orderDTO = orderService.findOne(response.getOrderId());

        if(orderDTO == null){
            log.error("【微信支付】异步通知，订单不存在, orderID={}", response.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //校验金额
        if(!MathUtil.equase(response.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】异步通知，订单金额不一致, orderID={},微信通知金额 = {},系统金额 = {}",
                    response.getOrderId(),
                    response.getOrderAmount(),
                    orderDTO.getOrderAmount().doubleValue());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单状态
        orderService.paid(orderDTO);

        return response;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest request = new RefundRequest();

        request.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        request.setOrderId(orderDTO.getOrderId());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信退款】request={}", JSON.toJSON(request));
        RefundResponse refund = BestPayService.refund(request);
        log.info("【微信退款】response={}", JSON.toJSON(refund));
        return refund;
    }


}
