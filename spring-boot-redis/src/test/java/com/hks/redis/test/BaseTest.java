package com.hks.redis.test;


import com.hks.redis.utils.CacheUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hks.redis.run.RedisApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述:测试类
 *
 * @author hekuangsheng
 * @create 2017-10-16 13:18
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class BaseTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {

        CacheUtils.hashSet("test", "ymq", "www.ymq.io");

        System.out.println(CacheUtils.hashGet("test", "ymq"));
    }

    @Test
    public void test2(){
        redisTemplate.opsForValue().set("heks","09012321");
        String result = redisTemplate.opsForValue().get("heks").toString();
        System.out.println(result);
    }

    @Test
    public void test3(){
        String result = redisTemplate.opsForValue().get("heks1").toString();
        System.out.println(result);
    }
}
