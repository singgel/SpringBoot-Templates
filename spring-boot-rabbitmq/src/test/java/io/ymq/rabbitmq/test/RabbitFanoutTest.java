package io.ymq.rabbitmq.test;

import io.ymq.rabbitmq.run.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 描述: 广播模式或者订阅模式队列
 *
 * @author: yanpenglei
 * @create: 2017/10/25 1:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class RabbitFanoutTest {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    public void sendPengleiTest() {

        String context = "此消息在，广播模式或者订阅模式队列下，有 FanoutReceiver1 FanoutReceiver2 可以收到";

        String routeKey = "topic.penglei.net";

        String exchange = "fanoutExchange";

        System.out.println("sendPengleiTest : " + context);

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }

    @Test
    public void sendSouyunkuTest() {

        String context = "此消息在，广播模式或者订阅模式队列下，有 FanoutReceiver1 FanoutReceiver2 可以收到";

        String routeKey = "topic.souyunku.com";

        String exchange = "fanoutExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendSouyunkuTest : " + context);

        this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }
}
