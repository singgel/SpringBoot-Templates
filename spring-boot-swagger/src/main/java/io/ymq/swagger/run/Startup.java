package io.ymq.swagger.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述:启动服务
 *
 * @author yanpenglei
 * @create 2017-10-26 16:37
 **/
@SpringBootApplication
@ComponentScan(value = {"io.ymq.swagger"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);

        /**
         *
         * 中文 http://127.0.0.1:8080/swagger/index.html
         *
         * 默认  http://127.0.0.1:8080/swagger-ui.html
         *
         * 在浏览器：http://127.0.0.1:8080/v2/api-docs  生成  swagger.yaml 文件内容
         *
         */

    }
}
