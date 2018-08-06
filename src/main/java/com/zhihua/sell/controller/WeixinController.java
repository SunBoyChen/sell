package com.zhihua.sell.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
public class WeixinController {

    private static final Logger log = LoggerFactory.getLogger(WeixinController.class);

    @GetMapping("/auth")
    public void auth(String code) {
        log.info("进入auth方法。。。");
        log.info("code={}", code);

        /*String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxd898fcb01713c658&secret=29d8a650db31472aa87800e3b0d739f2&code=" + code + "&grant_type=authorization_code";
*/
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc23a7f4dbee63aba&secret=794696726e7f3495e9fa4e23aca0ded9&grant_type=authorization_code";



        url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc23a7f4dbee63aba&secret=794696726e7f3495e9fa4e23aca0ded9&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }
}
