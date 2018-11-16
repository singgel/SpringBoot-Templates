package com.hks.lucene.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述:
 *
 * @author: hekuangsheng
 * @create: 2017/11/7 17:02
 */
@SpringBootApplication
@ComponentScan(value = {"com.hks.lucene"})
public class LuceneApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuceneApplication.class, args);
    }
}
