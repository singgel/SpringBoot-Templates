package com.hks.dubbo.provider.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述:启动提供方服务
 *
 * @author yanpenglei
 * @create 2017-10-27 11:49
 **/
@SpringBootApplication
@ComponentScan(value = {"com.hks.dubbo"})
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }
}
