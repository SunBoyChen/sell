package com.zhihua.sell.service.impl;

import com.zhihua.sell.converter.OrderMaster2OrderDTOConverter;
import com.zhihua.sell.dao.OrderDetailDao;
import com.zhihua.sell.dao.OrderMasterDao;
import com.zhihua.sell.dto.CartDTO;
import com.zhihua.sell.dto.OrderDTO;
import com.zhihua.sell.enums.OrderStatusEnums;
import com.zhihua.sell.enums.PayStatusEnums;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.pojo.OrderDetail;
import com.zhihua.sell.pojo.OrderMaster;
import com.zhihua.sell.pojo.ProductInfo;
import com.zhihua.sell.service.OrderService;
import com.zhihua.sell.service.PayService;
import com.zhihua.sell.service.ProductService;
import com.zhihua.sell.service.PushMessageService;
import com.zhihua.sell.utils.KeyUtil;
import com.zhihua.sell.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private PayService payService;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private PushMessageService pushMessageService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //生成订单Id
        String orderId = KeyUtil.genUniqueKey();

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品（数量, 价格）

        for (OrderDetail orderDetail :orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            //当商品不存在提示
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);

            //计算订单总价格
            orderAmount =  productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //2.保存订单详情
            orderDetailDao.save(orderDetail);
        }

        //3.保存订单数据
        OrderMaster orderMaster = new OrderMaster();

        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //4.减少库存
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream()
                .map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOS);

        //发送消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> detailList = orderDetailDao.findByOrderId(orderId);
        if(detailList.isEmpty()){
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(detailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> convert = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(convert,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster order = new OrderMaster();
        //查询订单状态

        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确，orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,order);
        orderMasterDao.save(order);

        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(detail -> {
                    return new CartDTO(detail.getProductId(),detail.getProductQuantity());
                })
                .collect(Collectors.toList());

       productService.increaseStock(cartDTOList);

        //如果已支付, 需要退款
       /* if(orderDTO.getPayStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }*/
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //查询订单状态
        if(!orderDTO.getOrderStatus() .equals( OrderStatusEnums.NEW.getCode())){
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        OrderMaster order = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,order);
        OrderMaster orderMaster = orderMasterDao.save(order);
        if (orderMaster == null) {
            log.error("【完结订单】更新失败, order={}", order);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送模板消息
        //spushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }


    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //查询订单状态
        if(!orderDTO.getOrderStatus().equals( OrderStatusEnums.NEW.getCode())){
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //查看支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDTO> convert = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(convert,pageable,orderMasterPage.getTotalElements());
    }
}
