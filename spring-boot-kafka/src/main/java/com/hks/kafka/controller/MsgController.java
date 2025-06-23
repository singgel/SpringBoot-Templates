package com.hks.kafka.controller;

import com.hks.kafka.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: singgel
 * @Date: 2018/11/16
 */
@RestController
@RequestMapping("/kafka")
public class MsgController {

    @Autowired
    MsgProducer msgProducer;

    @RequestMapping("/add")
    public String add(String name){
        try{
            msgProducer.sendMessage("topic-1",name);
        }
        catch (Exception ex){
            return ex.getMessage();
        }
        return "success";
    }
}
