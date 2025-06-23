package com.hks.dubbo.test;

import com.hks.dubbo.consumer.run.DubboConsumerApplication;
import com.hks.dubbo.consumer.service.ConsumerDemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述: 测试消费远程服务
 *
 * @author singgel
 * @create 2017-10-27 14:15
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboConsumerApplication.class)
public class ConsumerTest {

    @Autowired
    private ConsumerDemoService consumerDemoService;

    @Test
    public void sayHello(){
        consumerDemoService.sayHello("Peng Lei");
    }
}
