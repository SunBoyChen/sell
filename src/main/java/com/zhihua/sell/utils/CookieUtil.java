package com.zhihua.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CookieUtil {


    public static void set(HttpServletResponse response,String name,
                           String value, int maxAge){

        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }



    public static Cookie  get(HttpServletRequest request, String name){

        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            List<Cookie> list = Stream.of(cookies)
                    .filter(c -> name.equals(c.getName())).collect(Collectors.toList());

            if(!list.isEmpty()){
                return list.get(0);
            }
        }

        return null;
    }

}
