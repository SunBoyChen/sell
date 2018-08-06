package com.zhihua.sell.service;

import com.zhihua.sell.dto.OrderDTO;

public interface PushMessageService {

    public void orderStatus(OrderDTO orderDTO) ;
}
