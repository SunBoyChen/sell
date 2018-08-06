package com.zhihua.sell.exception;

import com.zhihua.sell.enums.ResultEnum;
import lombok.Getter;


public class SellException extends RuntimeException {

    private Integer code;

    public SellException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
