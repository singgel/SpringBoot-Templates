package io.ymq.example.rocketmq;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-02-02 16:50
 **/
public class test {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("1", "3", "2", "4");

        Collections.sort(names,(a,b) -> b.compareTo(a));

        System.out.println(names);



    }
}
