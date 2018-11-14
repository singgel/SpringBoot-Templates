package io.ymq.kafka;


import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * 描述:消息生产者
 *
 * @author yanpenglei
 * @create 2017-10-16 18:28
 **/
@Component
public class MsgProducer {

    private static final Logger log = LoggerFactory.getLogger(MsgProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topicName, String jsonData) {
        log.info("向kafka推送数据:[{}]", jsonData);
        try {
            kafkaTemplate.send(topicName, jsonData);
        } catch (Exception e) {
            log.error("发送数据出错！！！{}{}", topicName, jsonData);
            log.error("发送数据出错=====>", e);
        }

        //消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
            }

            @Override
            public boolean isInterestedInSuccess() {
                log.info("数据发送完毕");
                return false;
            }
        });
    }

}
