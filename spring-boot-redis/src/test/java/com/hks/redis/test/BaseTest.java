package com.hks.redis.test;


import com.hks.redis.utils.CacheUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import com.hks.redis.run.RedisApplication;
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

    @Test
    public void test() throws Exception {

        CacheUtils.hashSet("test", "ymq", "www.ymq.io");

        System.out.println(CacheUtils.hashGet("test", "ymq"));
    }

}
