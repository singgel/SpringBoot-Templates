package com.heks.demo.controller;

import com.heks.demo.model.ZkClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ZkController {

    @Resource
    ZkClient zkClient;

    /**
     *
     * @param path /aa/bb
     * @return
     */
    @RequestMapping(value = "addNode")
    public String insertZkNode(String path){
        try{
            zkClient.register(path );
        } catch (Exception ex){
            return ex.getMessage();
        }
        return "SUCCESS";
    }

    /**
     *
     * @param path /aa
     * @return
     */
    @RequestMapping(value = "searchNode")
    public String searchNode(String path){
        return zkClient.getChildren(path).toString();
    }

}
