package io.ymq.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述: 接收者
 *
 * @author: yanpenglei
 * @create: 2017/10/23 14:15
 */
@Component
@RabbitListener(queues = "topic.ymq")
public class TopicReceiver3 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("接收者 TopicReceiver3," + message);
    }

}
