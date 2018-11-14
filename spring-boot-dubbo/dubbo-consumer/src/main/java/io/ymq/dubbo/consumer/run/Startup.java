package io.ymq.dubbo.consumer.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述: 启动服务
 *
 * @author yanpenglei
 * @create 2017-10-27 11:49
 **/
@SpringBootApplication
@ComponentScan(value = {"io.ymq.dubbo"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }
}
