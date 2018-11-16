package com.hks.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述: 接收者
 * @author: hekuangsheng
 * @create: 2017/10/25 0:49
 */
@Component
@RabbitListener(queues = "direct")
public class DirectReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("接收者 DirectReceiver," + message);
    }
}
