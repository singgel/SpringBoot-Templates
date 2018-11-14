package io.ymq.redis.config;

import io.ymq.redis.utils.CacheUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 描述: 注册配置类到容器
 *
 * @author yanpenglei
 * @create 2017-10-16 14:50
 **/

@Configuration
@Import({RedisConfig.class, CacheUtils.class})
public class RedisAutoConfiguration {

}