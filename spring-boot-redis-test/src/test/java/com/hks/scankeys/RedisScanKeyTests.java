package com.hks.scankeys;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisScanKeyTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testScan() {

        Set<Object> binaryKeys = new HashSet<>();

        Set<Object> execute = redisTemplate.execute(new RedisCallback<Set<Object>>() {

            @Override
            public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {

                Cursor<byte[]> cursor = connection.scan( new ScanOptions.ScanOptionsBuilder().match("1145multi*").count(1000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;
            }
        });

        System.out.println(binaryKeys.size());

    }

    @Test
    public void testSScan() {

        Set<String> binaryvalues = new HashSet<>();

        ScanOptions scanOptions = ScanOptions.scanOptions().count(1).match("*").build();
        Cursor<ZSetOperations.TypedTuple<String>> cursor = redisTemplate.opsForZSet().scan("indicator:minute:69297:20190320", scanOptions);
        while(cursor.hasNext()) {
            String entry = cursor.next().getValue();
            binaryvalues.add(entry);
        }

        System.out.println(binaryvalues.size());

    }

    @Test
    public void testHScan() {

        Set<Map.Entry<Object, Object>> entrySet = new HashSet<>();

        ScanOptions scanOptions = ScanOptions.scanOptions().count(1).match("*").build();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan("Real_Gps", scanOptions);
        while(cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            entry.getKey();
            entry.getValue();
            entrySet.add(entry);
        }

        System.out.println(entrySet.size());

    }
}
