package com.zhihua.sell.controller;

import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    private static final Logger log = LoggerFactory.getLogger(SellerOrderController.class);


    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size){

        Pageable pageable = new PageRequest(page - 1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageable);
        Map<String,Object> map = new HashMap<>();
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);


        return new ModelAndView("order/list", map);
    }

    //http://sell.springboot.cn/sell/seller/order/cancel?orderId=340109305174819038


    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId
                            ) {
        Map<String, Object> map = new HashMap<>();
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e) {
            log.error("【卖家端取消订单】发生异常{}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg",  ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }


    /**
     * 完结
     * @param orderId
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId){

        Map<String, Object> map = new HashMap<>();
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e) {
            log.error("【卖家端完结订单】发生异常{}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg",  ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }


    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId){

        Map<String, Object> map = new HashMap<>();
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e){
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }
}
