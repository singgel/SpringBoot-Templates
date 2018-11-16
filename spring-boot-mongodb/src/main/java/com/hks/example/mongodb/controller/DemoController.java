package com.hks.example.mongodb.controller;

import com.hks.example.mongodb.dao.DemoDao;
import com.hks.example.mongodb.pojo.DemoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/16
 */
@RestController
@RequestMapping("/mongo")
public class DemoController {

    @Autowired
    DemoDao demoDao;

    @RequestMapping("/add")
    public String add(String name){
        try{
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setId(1000000L);
            demoEntity.setTitle(name);
            demoEntity.setDescription(name);
            demoEntity.setUrl("www.baidu.com");
            demoEntity.setBy(name);
            demoDao.saveDemo(demoEntity);
        }
        catch (Exception ex){
            return ex.getMessage();
        }
        return "success";
    }

    @RequestMapping("get")
    public DemoEntity get(Long id){
        return demoDao.findDemoById(id);
    }
}
