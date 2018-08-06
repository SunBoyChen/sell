package com.zhihua.sell.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 页面返回的数据格式
 * @param <T>
 */
@Data
public class ResultVO<T> implements Serializable {

    private Integer code;

    private  String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }



}
