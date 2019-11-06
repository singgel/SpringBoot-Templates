package com.heks.http.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

/**
 * AsyncRestTemplate客户端连接池配置
 *
 * 这个AsyncRestTemplate从没用过而且
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AsyncRestTemplateConfig {

    @Autowired
    CloseableHttpAsyncClient asyncClient;

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate(asyncHttpRequestFactory());
    }

    private AsyncClientHttpRequestFactory asyncHttpRequestFactory() {
        return new HttpComponentsAsyncClientHttpRequestFactory(asyncClient);
    }
}
