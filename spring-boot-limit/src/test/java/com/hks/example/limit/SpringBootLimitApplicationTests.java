package com.hks.example.limit;


import com.hks.example.RateLimitClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootLimitApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootLimitApplicationTests.class);


    @Autowired
    private ThreadPoolTaskExecutor consumerExecutor;


    @Autowired
    private RateLimitClient rateLimitClient;

    @Test
    public void redisLuaScriptTest() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            // 令牌桶的线程安全验证
            consumerExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    int requireToken = random.nextInt(1000);
                    LOGGER.info("requireToken:{}", requireToken);

                    List<Long> intervalAndToken = rateLimitClient.acquireIntervalAndToken("RATE_1000", requireToken);
                    LOGGER.info("requireToken:{}, acquireToken:{} ", requireToken, intervalAndToken);
                }
            });
        }
    }



}
