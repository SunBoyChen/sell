package com.zhihua.sell.utils;

import com.zhihua.sell.enums.CodeEnum;

public class EnumUtil {

    public static<T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for (T t:enumClass.getEnumConstants()) {
            if(t.getCode().equals(code)){
                return t;
            }
        }
        return null;
    }
}
