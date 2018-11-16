package com.hks.redis.config;

import com.hks.redis.utils.CacheUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 描述: 注册配置类到容器
 *
 * @author hekuangsheng
 * @create 2017-10-16 14:50
 **/

@Configuration
@Import({RedisConfig.class, CacheUtils.class})
public class RedisAutoConfiguration {

}