package com.hks.example.controller;

import com.hks.example.annotation.RateLimit;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 描述: 测试页
 *
 * @author singgel
 * @create 2018-08-16 15:42
 **/
@RestController
public class LimiterController {

    @Autowired
    private RedisTemplate redisTemplate;

    // 10 秒中，可以访问10次
    @RateLimit(key = "test", time = 10, count = 10)
    @GetMapping("/test")
    public String luaLimiter() {
        RedisAtomicInteger entityIdCounter = new RedisAtomicInteger("entityIdCounter", redisTemplate.getConnectionFactory());

        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");

        return date + " 累计访问次数：" + entityIdCounter.getAndIncrement();
    }
}
