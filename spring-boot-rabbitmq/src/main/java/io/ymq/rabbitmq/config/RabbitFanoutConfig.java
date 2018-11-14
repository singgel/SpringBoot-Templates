package io.ymq.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:配置广播模式或者订阅模式队列
 * <p>
 * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
 *
 * @author yanpenglei
 * @create 2017-10-16 16:47
 **/
@Configuration
public class RabbitFanoutConfig {

    final static String PENGLEI = "fanout.penglei.net";

    final static String SOUYUNKU = "fanout.souyunku.com";
    @Bean
    public Queue queuePenglei() {
        return new Queue(RabbitFanoutConfig.PENGLEI);
    }

    @Bean
    public Queue queueSouyunku() {
        return new Queue(RabbitFanoutConfig.SOUYUNKU);
    }

    /**
     * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有队列上。
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeQueuePenglei(Queue queuePenglei, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queuePenglei).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeQueueSouyunku(Queue queueSouyunku, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueSouyunku).to(fanoutExchange);
    }

}
