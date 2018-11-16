package com.hks.rabbitmq.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述: 启动服务
 *
 * @author: hekuangsheng
 * @create: 2017/10/23 14:14
 */
@SpringBootApplication
@ComponentScan(value = {"com.hks.rabbitmq"})
public class RabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
    }
}
