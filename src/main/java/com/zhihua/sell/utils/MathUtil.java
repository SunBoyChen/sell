package com.zhihua.sell.utils;

public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    /**
     * 比较2个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equase(Double d1 ,Double d2){
        Double result = Math.abs(d2 - d1);
        if(result < MONEY_RANGE){
            return  true ;
        }else {
            return  false;
        }
    }
}
