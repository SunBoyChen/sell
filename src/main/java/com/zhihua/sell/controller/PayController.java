package com.zhihua.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.pojo.OrderMaster;
import com.zhihua.sell.service.OrderService;
import com.zhihua.sell.service.PayService;
import com.zhihua.sell.service.impl.OrderServiceImpl;
import com.zhihua.sell.utils.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    private final static Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                        @RequestParam("returnUrl") String returnUrl){

        //查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);

        //orderDTO.getOrderAmount().compareTo()
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        Map<String,Object> map = new HashMap<>();
        //发起支付
        //PayResponse payResponse = payService.create(orderDTO);
        //测试使用
        PayResponse payResponse = new PayResponse();
        payResponse.setAppId("appid");
        payResponse.setTimeStamp("timeStamp");
        payResponse.setNonceStr("12345");
        payResponse.setPackAge("packAge");
        payResponse.setPaySign("paySign");

        map.put("payResponse",payResponse);
        map.put("returnUrl","returnUrl");


        return new ModelAndView("pay/create",map);




    }


    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {

        //1.验证签名
        //2.支付状态
        //3.支付金额
        //4.支付人（下单人 == 支付人）

        PayResponse response = payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
