package com.hks.assembly;

import com.hks.assembly.util.ClassPathFileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    private static final String UTF8 = "UTF-8";
    private static final String TEST_JKS = "test.jks";
    private static final String TEST_SQL = "db/test.sql";
    private static final String TEST_TXT = "test.txt";

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void contextLoads() {
        threadPoolTaskExecutor.execute(()->{
            System.out.println("-----------1---------");

            System.out.println("-----------2---------");

            //block();

            System.out.println("-----------3---------");
        });
    }

    private void block(){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
