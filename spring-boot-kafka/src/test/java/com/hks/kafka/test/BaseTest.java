package com.hks.kafka.test;

import com.hks.kafka.MsgProducer;
import com.hks.kafka.run.KafkaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 描述: 测试 kafka
 *
 * @author hekuangsheng
 * @create 2017-10-16 18:45
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaApplication.class)
public class BaseTest {

    @Autowired
    private MsgProducer msgProducer;

    @Test
    public void test() throws Exception {

        msgProducer.sendMessage("topic-1", "topic--------1");
        msgProducer.sendMessage("topic-2", "topic--------2");
    }
}
