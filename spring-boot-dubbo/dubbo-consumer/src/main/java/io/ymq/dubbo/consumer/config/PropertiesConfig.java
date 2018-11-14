package io.ymq.dubbo.consumer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 描述:  加载配置
 *
 * @author yanpenglei
 * @create 2017-10-27 13:26
 **/
@Configuration
@PropertySource("classpath:dubbo.properties")
@ImportResource({"classpath:dubbo/*.xml"})
public class PropertiesConfig {
}
