package com.hks.example.springboot.rabbitmq.ack.web;

import com.hks.example.springboot.rabbitmq.ack.producer.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private HelloSender helloSender;

    /**
     * 单生产者-单个消费者
     */
    @RequestMapping("/send")
    public void sendMessageTest(String context) throws Exception {
        helloSender.sendMessage(context);
    }

}
