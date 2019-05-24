package com.hks.pubsub;

import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * Redis message publisher
 *
 * @author hekuangsheng@gmail.com
 * @create 2019-05-01 19:36
 **/
@Setter
public class RedisMessagePublisher implements MessagePublisher {

    private RedisTemplate<String, String> redisTemplate;

    private ChannelTopic topic;

    private RedisMessagePublisher() {
    }

    public RedisMessagePublisher(RedisTemplate<String, String> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
