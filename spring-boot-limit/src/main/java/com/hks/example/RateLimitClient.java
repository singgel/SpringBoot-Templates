package com.hks.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 利用Redis进行限流，解决分布式、高TPS的问题
 */
@Service
public class RateLimitClient {
    private final static long PERMITS_PER_SECOND = 3250;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Qualifier("getRedisScript")
    @Resource
    private RedisScript<List> ratelimitLua;

    @Qualifier("getInitRedisScript")
    @Resource
    private RedisScript<Long> ratelimitInitLua;

    /**
     * 初始化令牌桶！！！
     *
     * @param key
     * @return
     */
    public Token initToken(String key) {
        Token token = Token.SUCCESS;
        if (stringRedisTemplate.hasKey(getKey(key))) {
            return token;
        }
        Long acquire = stringRedisTemplate.execute(ratelimitInitLua,
                Collections.singletonList(getKey(key)), String.valueOf(PERMITS_PER_SECOND), String.valueOf(PERMITS_PER_SECOND));
        if (acquire == 1) {
            token = Token.SUCCESS;
        } else if (acquire == 0) {
            token = Token.SUCCESS;
        } else {
            token = Token.FAILED;
        }
        return token;
    }

    /**
     * 根据请求值去令牌桶获取，之所以用Long是因为lua脚本返回值用Integer接收有问题
     * lua要不直接返回json然后转map，为了省事直接返回list
     *
     * list.get(0) 距离下一次刷新的最小时间间隔，单位：微秒
     * list.get(1) 获取到的令牌数
     *
     * @param key
     * @param permits
     * @return
     */
    public List<Long> acquireIntervalAndToken(String key, Integer permits){
        List intervalAndToken = stringRedisTemplate.execute(ratelimitLua,
                Collections.singletonList(getKey(key)), permits.toString());
        return intervalAndToken;
    }

    public String getKey(String key) {
        return Constants.RATE_LIMIT_KEY + key;
    }

}

