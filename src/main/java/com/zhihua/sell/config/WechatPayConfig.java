package com.zhihua.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class WechatPayConfig {

    @Value("${wechat.mpAppId}")
    private String appId;

    @Value("${wechat.mpAppSecret}")
    private String secretId;

    /**
     * 商户号
     */
    @Value("${wechat.mchId}")
    private String mchId;

    /**
     * 商户密钥
     */
    @Value("${wechat.mchKey}")
    private String mchKey;

    /**
     * 商户证书路径
     */
    @Value("${wechat.keyPath}")
    private String keyPath;

    @Value("${wechat.notifyUrl}")
    private String notifyUrl;


    @Bean
    public BestPayService bestPayService(){

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return  bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(appId);
        wxPayH5Config.setAppSecret(secretId);
        wxPayH5Config.setMchId(mchId);
        wxPayH5Config.setMchKey(mchKey);
        wxPayH5Config.setKeyPath(keyPath);
        wxPayH5Config.setNotifyUrl(notifyUrl);
        return wxPayH5Config;
    }



}
