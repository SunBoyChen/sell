package com.zhihua.sell.handle;

import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.exception.SellerAuthorizeException;
import com.zhihua.sell.utils.ResultVOUtil;
import com.zhihua.sell.vo.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {


    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        //重定向到认证页面
        return new ModelAndView("redirect:http://localhost:8080/sell/index.html");
    }

    //处理自定义异常
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)    //指定错误码
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

}
