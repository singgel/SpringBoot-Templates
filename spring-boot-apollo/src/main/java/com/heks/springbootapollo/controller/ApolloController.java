package com.heks.springbootapollo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heks
 * @description: TODO
 * @date 2020/9/29
 */
@RestController
public class ApolloController {
    // apollo的spring里面使用的话key不要用：符号
    @Value("${apollo:json}")
    private String json;
    @Value("${apollo.str}")
    private String str;

    @RequestMapping("/json")
    public String testJson() {
        return json;
    }

    @RequestMapping("/str")
    public String testStr() {
        return str;
    }
}
