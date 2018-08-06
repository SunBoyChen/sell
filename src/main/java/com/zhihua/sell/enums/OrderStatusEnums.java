package com.zhihua.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnums implements CodeEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;
    private String message;


    OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /*public static OrderStatusEnums getOrderStatusEnums(Integer code){

        for(OrderStatusEnums order: OrderStatusEnums.values()){
            if(order.getCode().equals(code)){
                return order;
            }

        }
        return  null;
    }*/
}
