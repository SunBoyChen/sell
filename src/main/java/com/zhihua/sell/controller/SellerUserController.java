package com.zhihua.sell.controller;

import com.sun.deploy.net.HttpResponse;
import com.zhihua.sell.constant.CookieConstant;
import com.zhihua.sell.constant.RedisConstant;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.pojo.SellerInfo;
import com.zhihua.sell.service.SellerService;
import com.zhihua.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        //1. openid去和数据库里的数据匹配
        SellerInfo seller = sellerService.findSellerInfoByOpenid(openid);
        if(null == seller){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        //2. 设置token至redis
        String token = UUID.randomUUID().toString();
            //过期时间
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //3. 设置token至cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,expire);

        return  new ModelAndView("redirect:http://localhost:8080/sell/seller/order/list");

    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        //2. 清除redis
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        //3. 清除cookie  过期时间设置为0即可
        CookieUtil.set(response,CookieConstant.TOKEN,null,0);

        Map<String,Object> map = new HashMap<>();
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }


    public static void main(String[] args) {
        String TOKEN_PREFIX = "token_%s_%s";
        String format = String.format(TOKEN_PREFIX,"456","dfg");
        System.out.println(format);


        String token = UUID.randomUUID().toString();
        //过期时间
        Integer expire = RedisConstant.EXPIRE;

        System.out.println(String.format(RedisConstant.TOKEN_PREFIX,token));
    }

}
