package com.hks.mybatis.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述: 启动服务
 *
 * @author hekuangsheng
 * @create 2017-10-19 18:23
 **/
@SpringBootApplication
@ComponentScan(value = {"com.hks.mybatis"})
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}