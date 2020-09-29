package com.hks.redis.controller;

import com.hks.redis.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author heks
 * @description: TODO
 * @date 2020/7/14
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value={"set"}, method= RequestMethod.GET)
    public String setUserList() {
        redisTemplate.opsForValue().set("heks","hh");
        return "OK";
    }

    @RequestMapping(value={"get"}, method= RequestMethod.GET)
    public String getUserList() {
        String heks = (String) redisTemplate.opsForValue().get("heks");
        return heks;
    }
}
