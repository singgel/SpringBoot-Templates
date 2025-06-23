package com.hks.pubsub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis Message Subscriber
 * <p>
 * RedisMessageSubscriber implements the Spring Data Redis-provided MessageListener interface
 *
 * @author singgel@gmail.com
 * @create 2019-05-01 19:39
 **/
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Component
public class RedisMessageSubscriber implements MessageListener {

    private List<String> messageList;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageList.add("[pattern:" + new String(pattern) + ",message:" + message.toString() + "]");
    }
}
