package com.hks.dubbo.consumer.web;

/**
 * 描述:
 *
 * @author hekuangsheng
 * @create 2018-07-25 17:25
 **/

import com.hks.dubbo.api.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DemoConsumerController {

    @Resource
    private DemoService demoService;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return demoService.sayHello(name);
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }
}