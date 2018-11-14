package io.ymq.rabbitmq.test;

import io.ymq.rabbitmq.run.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述: 默认的交换机模式
 *
 * @author: yanpenglei
 * @create: 2017/10/25 1:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class RabbitDirectTest {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    public void sendHelloTest() {

        String context = "此消息在，默认的交换机模式队列下，有 helloReceiver 可以收到";

        String routeKey = "hello";

        context = "routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendHelloTest : " + context);

        this.rabbitTemplate.convertAndSend(routeKey, context);
    }

    @Test
    public void sendDirectTest() {

        String context = "此消息在，默认的交换机模式队列下，有 DirectReceiver 可以收到";

        String routeKey = "direct";

        String exchange = "directExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendDirectTest : " + context);

        // 推荐使用 sendHello（） 方法写法，这种方式在 Direct Exchange 多此一举，没必要这样写
        this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }


}
