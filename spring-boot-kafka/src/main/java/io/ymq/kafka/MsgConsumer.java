package io.ymq.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 描述:消息消费者
 *
 * @author yanpenglei
 * @create 2017-10-16 18:33
 **/
@Component
public class MsgConsumer {

    @KafkaListener(topics = {"topic-1","topic-2"})
    public void processMessage(String content) {

        System.out.println("消息被消费"+content);
    }

}