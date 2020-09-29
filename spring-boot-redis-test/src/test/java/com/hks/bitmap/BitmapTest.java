package com.hks.bitmap;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import sun.jvm.hotspot.utilities.BitMap;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BitmapTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void setBitmap() {
        Instant beginTime1 = Instant.now();
        for (int i = 0; i < (100 * 10000); i++) {
            redisTemplate.opsForValue().setBit("stringValue",i,true);
        }
        log.info(" ################# setBit time duration : {}", Duration.between(beginTime1, Instant.now()).getSeconds());
    }

    @Test
    public void getBitmap() {
        Instant beginTime = Instant.now();
        String newStringValue = redisTemplate.opsForValue().get("stringValue");
        System.out.println(newStringValue);
        log.info(" ################# get time duration : {}", Duration.between(beginTime, Instant.now()).getSeconds());
    }
}
