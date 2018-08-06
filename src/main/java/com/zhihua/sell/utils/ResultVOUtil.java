package com.zhihua.sell.utils;

import com.zhihua.sell.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO vo = new ResultVO();
        vo.setCode(0);
        vo.setData(object);
        vo.setMsg("成功");
        return vo;
    }


    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO vo = new ResultVO();
        vo.setCode(code);
        vo.setMsg(msg);
        return vo;
    }
}
