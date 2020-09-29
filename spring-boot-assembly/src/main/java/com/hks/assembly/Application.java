package com.hks.assembly;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Spring Boot 项目启动入口
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.hks.assembly.mapper")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        // 当前项目环境
        log.info("spring.profiles.active = " + environment.getProperty("spring.profiles.active"));
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        log.info("contextPath = " + contextPath);
        log.info("server.port = " + port);
        log.info("hello = " + environment.getProperty("hello"));

        log.info("http://localhost:" + port + contextPath + "/hello?name=spring-boot-assembly");
        log.info("http://localhost:" + port + contextPath + "/otherFile");

    }

    @Bean
    public ThreadPoolTaskExecutor backExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(2);
        executor.setKeepAliveSeconds(20);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(10);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
