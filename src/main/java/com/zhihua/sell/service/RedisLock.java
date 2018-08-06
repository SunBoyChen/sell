package com.zhihua.sell.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLock {

    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public boolean lock(String key, String value) {
        //当key存在时候返回false，不存在返回true
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    public void unlock(String key, String value) {
        String currentVaule = stringRedisTemplate.opsForValue().get(key);
        try {
            if (!StringUtils.isEmpty(currentVaule) && currentVaule.equals(value)) {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

    }
}
