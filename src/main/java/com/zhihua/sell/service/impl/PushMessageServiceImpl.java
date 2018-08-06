package com.zhihua.sell.service.impl;

import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.service.PushMessageService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PushMessageServiceImpl implements PushMessageService {

    private static final Logger log = LoggerFactory.getLogger(PushMessageServiceImpl.class);

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void orderStatus(OrderDTO orderDTO) {

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();

        templateMessage.setTemplateId("NyC8pQSfgZSr-pj6ptjMC8JplAazup9m4wt6FHW617g");
        templateMessage.setToUser("oWvjE1HghNJqPEgE8cXgVe-LuEu8");
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18868812345"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnums().getMessage()),
                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }


    }


}
