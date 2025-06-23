package com.hks.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * Redis Pub/Sub tests
 *
 * @author singgel@gmail.com
 * @create 2019-05-01 19:12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisPubSub {

    @Autowired
    @Qualifier("redisPublisherForTopic1")
    private MessagePublisher redisPublisher1;

    @Autowired
    @Qualifier("redisPublisherForTopic2")
    private MessagePublisher redisPublisher2;

    @Autowired
    @Qualifier("messageListener1")
    private RedisMessageSubscriber subscriber1;

    @Autowired
    @Qualifier("messageListener2")
    private RedisMessageSubscriber subscriber2;


    @Test
    public void test1() {

        // 循环发布10次消息, 主要方法 redisTemplate.convertAndSend
        for (int i = 0; i < 10; i++) {
            String message = "Topic1 Message : " + UUID.randomUUID();
            redisPublisher1.publish(message);
        }

        // 循环发布10次消息, 主要方法 redisTemplate.convertAndSend
        for (int i = 0; i < 10; i++) {
            String message = "Topic2 Message : " + UUID.randomUUID();
            redisPublisher2.publish(message);
        }

        // 订阅消息
        List<String> messageList1 = subscriber1.getMessageList();
        for (int i = 0; i < messageList1.size(); i++) {
            log.info(messageList1.get(i));
        }

        // 订阅消息
        List<String> messageList2 = subscriber2.getMessageList();
        for (int i = 0; i < messageList2.size(); i++) {
            log.info(messageList2.get(i));
        }

        // foreach 循环会报错： ConcurrentModificationException
        // 解决： https://www.cnblogs.com/dolphin0520/p/3933551.html
        /*for (String msg : messageList) {
            log.info(msg);
        }*/

    }

}
