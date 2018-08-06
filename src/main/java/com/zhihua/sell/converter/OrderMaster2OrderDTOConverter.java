package com.zhihua.sell.converter;


import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.pojo.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasters) {
        return orderMasters.stream().map(e -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(e, orderDTO);
            return orderDTO;
        }).collect(Collectors.toList());
    }


}