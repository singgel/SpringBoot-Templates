package io.ymq.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-25 0:47
 **/
@Component
@RabbitListener(queues = "hello")
public class helloReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("接收者 helloReceiver," + message);
    }
}
