package com.souyunku.example.springboot.rabbitmq.ack.producer;

import com.souyunku.example.springboot.rabbitmq.ack.receiver.HelloReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloSender implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {

    private Logger logger = LoggerFactory.getLogger(HelloSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String context) {

        // 消息发送失败返回到队列中, application.properties 配置 spring.rabbitmq.publisher-returns=true
        rabbitTemplate.setMandatory(true);

        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(this);

        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                logger.info("HelloSender 发送失败：" + cause + correlationData.toString());
            } else {
                logger.info("HelloSender 发送成功");
            }
        });

        logger.info("HelloSender 发送的消息内容：{}", context);

        this.rabbitTemplate.convertAndSend("hello", context);

    }


    /**
     * 失败后返回消息回调
     * <p>
     * 当消息发送出去找不到对应路由队列时，将会把消息退回
     * 如果有任何一个路由队列接收投递消息成功，则不会退回消息
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
    }

    /**
     * 实现ConfirmCallback
     * <p>
     * ACK=true仅仅标示消息已被Broker接收到，并不表示已成功投放至消息队列中
     * ACK=false标示消息由于Broker处理错误，消息并未处理成功
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("消息id: " + correlationData + "确认" + (ack ? "成功:" : "失败"));
    }



}
