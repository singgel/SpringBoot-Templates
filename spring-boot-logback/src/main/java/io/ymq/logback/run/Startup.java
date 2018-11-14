package io.ymq.logback.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-30 14:15
 **/
@SpringBootApplication
@ComponentScan(value = {"io.ymq.logback"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }
}
