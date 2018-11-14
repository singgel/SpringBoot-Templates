package io.ymq.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述:接收者
 *
 * @author yanpenglei
 * @create 2017-10-23 15:38
 **/
@Component
@RabbitListener(queues = "fanout.souyunku.com")
public class FanoutReceiver2 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("接收者 FanoutReceiver2," + message);
    }
}
