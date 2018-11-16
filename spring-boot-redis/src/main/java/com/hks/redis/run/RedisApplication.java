package com.hks.redis.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 描述: 启动服务
 *
 * @author hekuangsheng
 * @create 2017-10-16 13:19
 **/
@SpringBootApplication
@ComponentScan(value = {"com.hks.redis"})
public class RedisApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(RedisApplication.class, args);

        while (true) {
            //为了简单起见，所有的异常信息都往外抛
            int port = 8989;
            //定义一个ServerSocket监听在端口8989上
            ServerSocket server = new ServerSocket(port);
            server.accept();
        }

    }
}
