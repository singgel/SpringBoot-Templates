package com.hks.solr.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述: 启动服务
 *
 * @author hekuangsheng
 * @create 2017-10-18 10:38
 **/
@SpringBootApplication
@ComponentScan(value = {"com.hks.solr"})
public class SolrApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolrApplication.class, args);
    }

}
