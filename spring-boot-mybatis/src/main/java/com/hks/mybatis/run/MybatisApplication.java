package com.hks.mybatis.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 描述: 启动服务
 *
 * @author singgel
 * @create 2017-10-19 18:23
 **/
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableTransactionManagement
@ComponentScan(value = {"com.hks.mybatis"})
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}