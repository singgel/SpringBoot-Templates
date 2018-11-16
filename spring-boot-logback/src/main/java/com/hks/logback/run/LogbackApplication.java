package com.hks.logback.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述:
 *
 * @author hekuangsheng
 * @create 2017-10-30 14:15
 **/
@SpringBootApplication
@ComponentScan(value = {"com.hks.logback"})
public class LogbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogbackApplication.class, args);
    }
}
