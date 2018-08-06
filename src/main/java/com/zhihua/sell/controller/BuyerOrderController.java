package com.zhihua.sell.controller;

import com.alibaba.fastjson.JSON;
import com.zhihua.sell.converter.OrderForm2OrderDTOConverter;
import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.from.OrderForm;
import com.zhihua.sell.pojo.OrderDetail;
import com.zhihua.sell.service.BuyerService;
import com.zhihua.sell.service.OrderService;
import com.zhihua.sell.utils.ResultVOUtil;
import com.zhihua.sell.vo.ResultVO;
import org.apache.el.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    private static final Logger log = LoggerFactory.getLogger(BuyerOrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO order = orderService.create(orderDTO);
        Map<String,String> data = new HashMap<>();
        data.put("orderId",order.getOrderId());
        ResultVO resultVO = ResultVOUtil.success(data);
        return resultVO;
    }


    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(String openid,
                                   @RequestParam(value = "page",defaultValue = "0" ) Integer page,
                                   @RequestParam(defaultValue = "10") Integer size){

        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageable);
        List<OrderDTO> content = orderDTOPage.getContent();
        ResultVO resultVO = ResultVOUtil.success(content);

        //null值问题
        return resultVO;
    }


    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(String openid,String orderId){
        //OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        ResultVO resultVO = ResultVOUtil.success(orderDTO);
        return resultVO;
    }

    @GetMapping("/cancel")
    public ResultVO cancel(String openid,String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
