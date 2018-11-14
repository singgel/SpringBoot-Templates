package io.ymq.dubbo.provider.service;

import com.alibaba.dubbo.rpc.RpcContext;
import io.ymq.dubbo.api.DemoService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述: 提供方实现
 *
 * @author yanpenglei
 * @create 2017-10-27 13:22
 **/
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }
    
}
