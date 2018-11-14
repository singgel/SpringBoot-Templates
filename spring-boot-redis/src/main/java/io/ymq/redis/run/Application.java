package io.ymq.redis.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 描述: 启动服务
 *
 * @author yanpenglei
 * @create 2017-10-16 13:19
 **/
@SpringBootApplication
@ComponentScan(value = {"io.ymq.redis"})
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);

        while (true) {
            //为了简单起见，所有的异常信息都往外抛
            int port = 8989;
            //定义一个ServerSocket监听在端口8989上
            ServerSocket server = new ServerSocket(port);
            server.accept();
        }

    }
}
