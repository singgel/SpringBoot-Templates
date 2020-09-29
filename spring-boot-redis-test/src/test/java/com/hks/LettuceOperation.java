package com.hks;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LettuceOperation {

    private static RedisClusterClient client = null;
    private static StatefulRedisClusterConnection<String, String> connect;

    /**************************************
     * log message
     **************************************/
    private static Logger log = LoggerFactory.getLogger(LettuceOperation.class);

    public static Logger getLog() {
        return log;
    }

    private String resultMessage = "the method: {0} exec result：\n{1}";
    private String execErrorMessage = "the {0} has error.";

    public String getResultMessage() {
        return resultMessage;
    }

    public String getExecErrorMessage() {
        return execErrorMessage;
    }

    /*************************************/


    @Before
    public void before() {
        /*ArrayList<RedisURI> list = new ArrayList<RedisURI>();
        list.add(RedisURI.create("redis://192.168.0.193:7001"));
        list.add(RedisURI.create("redis://192.168.0.193:7002"));
        list.add(RedisURI.create("redis://192.168.0.193:7003"));
        client = RedisClusterClient.create(list);*/
        RedisClusterClient client=RedisClusterClient.create("redis://127.0.0.1:6379");
        connect = client.connect();
        connect.setAutoFlushCommands(false);
    }

    /**
     * command:info
     */
    @Test
    public void info() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.info();
        loggingResultMessage(result);
    }

    /**
     * command:cluster_info
     */
    @Test
    public void cluster_info() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.clusterInfo();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:cluster_delete_slots
     */
    @Test
    public void cluster_delete_slots() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.clusterDelSlots(5461);
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:cluster_add_slots
     */
    @Test
    public void cluster_add_slots() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.clusterAddSlots(5461);
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }


    /**
     * command:bg_rewrite_aof
     */
    @Test
    public void bg_rewrite_aof() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.bgrewriteaof();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:slave_of_no_one
     * ERR SLAVEOF not allowed in cluster mode.
     */
    @Test
    public void slave_of_no_one() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.slaveofNoOne();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:bg_save
     */
    @Test
    public void bg_save() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.bgsave();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:debug
     */
    @Test
    public void debug() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.debugObject("a1");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:meet
     */
    @Test
    public void meet() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.clusterMeet("192.168.0.193", 7002);
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:forget
     */
    @Test
    public void forget() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.clusterForget("d5998dacf6d33a2a7c778d63af97bfaecd08effe");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:dbsize
     */
    @Test
    public void dbsize() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<Long> result = asyncCommands.dbsize();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:keys
     */
    @Test
    public void keys() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<List<String>> result = asyncCommands.keys("*");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:info_section
     */
    @Test
    public void info_section() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.info("memory");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:nodes
     */
    @Test
    public void nodes() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.clusterNodes();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:ping
     */
    @Test
    public void ping() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.ping();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:flushdb
     */
    @Test
    public void flushdb() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.flushdb();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:flushall
     */
    @Test
    public void flushall() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.flushall();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:save
     */
    @Test
    public void save() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.save();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:lastsave
     */
    @Test
    public void lastsave() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<Date> result = asyncCommands.lastsave();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:random_key
     */
    @Test
    public void random_key() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.randomkey();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:dump
     */
    @Test
    public void dump() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<byte[]> result = asyncCommands.dump("a1");
        getConnect().flushCommands(); // commit;

        Object[] args = new Object[0];
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            args = new Object[]{
                    methodName,
                    new String(result.get()),
            };
        } catch (Exception e) {
            loggingErrorMessage(methodName, e);
        }

        getLog().info(MessageFormat.format(getResultMessage(), args));
    }

    /**
     * command:slow_log_get
     */
    @Test
    public void slow_log_get() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<List<Object>> result = asyncCommands.slowlogGet();
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:set
     */
    @Test
    public void set() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.set("a1","hyr1");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:get
     */
    @Test
    public void get() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.get("a1");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:hset
     */
    @Test
    public void hset() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<Boolean> result = asyncCommands.hset("group1","key1","g1_k1_data");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:hgetall
     */
    @Test
    public void hgetall() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<Map<String, String>> result = asyncCommands.hgetall("group1");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:hget
     */
    @Test
    public void hget() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.hget("group1","key1");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:lpush
     */
    @Test
    public void lpush() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<Long> result = asyncCommands.lpush("myqueue","data1");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:lpop
     */
    @Test
    public void lpop() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<String> result = asyncCommands.lpop("myqueue");
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * command:bitmap
     */
    @Test
    public void bitmap() {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = getConnect().async();
        RedisFuture<Long> result = asyncCommands.setbit("myqueue",1L,1);
        getConnect().flushCommands(); // commit;

        loggingResultMessage(result);
    }

    /**
     * logging the exec result
     *
     * @param info
     */
    private void loggingResultMessage(RedisFuture info) {
        Object[] args = new Object[0];
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        try {
            args = new Object[]{
                    methodName,
                    info.get()
            };
        } catch (Exception e) {
            loggingErrorMessage(methodName, e);
        }

        getLog().info(MessageFormat.format(getResultMessage(), args));
    }

    public static RedisClusterClient getClient() {
        return client;
    }

    public static StatefulRedisClusterConnection<String, String> getConnect() {
        return connect;
    }

    /**
     * logging exec error message
     *
     * @param methodName
     * @param e
     */
    private void loggingErrorMessage(String methodName, Exception e) {
        Object[] args = {
                methodName
        };
        getLog().error(MessageFormat.format(getExecErrorMessage(), args), e);
    }

    @After
    public void after() {
        connect.close();
        client.shutdown();
    }
}
