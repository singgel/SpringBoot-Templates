package com.hks.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.ArrayList;

/**
 * config
 *
 * @author singgel@gmail.com
 * @create 2019-05-01 19:57
 **/
@Configuration
@ComponentScan("net.singgel@gmail.com.tech.designpattern.*")
@EnableRedisRepositories(basePackages = "net.singgel@gmail.com.tech.designpattern.*")
public class RedisPubSubConfig {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Bean
    RedisMessageListenerContainer redisContainer() {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(redisTemplate.getConnectionFactory());

        container.addMessageListener(messageListenerAdapter1() , topic1());
        container.addMessageListener(messageListenerAdapter1() , topic2());

        container.addMessageListener(messageListenerAdapter2(), topic2());

        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter1() {
        return new MessageListenerAdapter(messageListener1());
    }

    @Bean
    public RedisMessageSubscriber messageListener1() {
        return new RedisMessageSubscriber(new ArrayList<>());
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter2() {
        return new MessageListenerAdapter(messageListener2());
    }

    @Bean
    public RedisMessageSubscriber messageListener2() {
        return new RedisMessageSubscriber(new ArrayList<>());
    }


    @Bean
    MessagePublisher redisPublisherForTopic1() {
        return new RedisMessagePublisher(redisTemplate, topic1());
    }

    @Bean
    MessagePublisher redisPublisherForTopic2() {
        return new RedisMessagePublisher(redisTemplate, topic2());
    }

    @Bean
    ChannelTopic topic1() {
        return new ChannelTopic("topic1");
    }

    @Bean
    ChannelTopic topic2() {
        return new ChannelTopic("topic2");
    }

}
