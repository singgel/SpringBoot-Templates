package com.example.use.controller;

import com.hks.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private ExampleService exampleService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String testStarter() {
        return exampleService.wrap("hello");
    }

}
