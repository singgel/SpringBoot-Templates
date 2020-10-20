package com.hks.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Value("${thread.consumer.core-pool-size}")
    private int consumerCorePoolSize;
    @Value("${thread.consumer.max-pool-size}")
    private int consumerMaxPoolSize;
    @Value("${thread.consumer.queue-capacity}")
    private int consumerQueueCapacity;

    @Bean("consumerExecutor")
    public ThreadPoolTaskExecutor consumerExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(consumerCorePoolSize);
        taskExecutor.setMaxPoolSize(consumerMaxPoolSize);
        taskExecutor.setQueueCapacity(consumerQueueCapacity);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("consumerExecutor-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();
        return taskExecutor;
    }

}

