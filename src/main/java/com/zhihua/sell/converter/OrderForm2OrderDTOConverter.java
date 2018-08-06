package com.zhihua.sell.converter;

import com.alibaba.fastjson.JSON;
import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.from.OrderForm;
import com.zhihua.sell.pojo.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderDTOConverter {

    private static final Logger log = LoggerFactory.getLogger(OrderForm2OrderDTOConverter.class);


    public static OrderDTO converter(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            orderDetails  = JSON.parseArray(orderForm.getItems(), OrderDetail.class);
        } catch (SellException e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;

    }
}
