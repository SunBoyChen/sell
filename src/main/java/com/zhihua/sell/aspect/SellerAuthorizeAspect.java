package com.zhihua.sell.aspect;


import com.zhihua.sell.constant.CookieConstant;
import com.zhihua.sell.constant.RedisConstant;
import com.zhihua.sell.exception.SellerAuthorizeException;
import com.zhihua.sell.utils.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.Configuration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 切面  用户登入之前进行拦截
 */
@Aspect
@Component
public class SellerAuthorizeAspect {

    private static final Logger log = LoggerFactory.getLogger(SellerAuthorizeAspect.class);

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 切点
     */
    @Pointcut("execution(public * com.zhihua.sell.controller.Seller*.*(..))" +
    "&& !execution(public * com.zhihua.sell.controller.SellerUserController.*(..))")
    public void verify() {}


    /**
     * 操作
     */
    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(null == cookie){
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        //去redis里查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }

    }
}
