package io.ymq.redis.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

/**
 * 描述: 操作redis 工具类
 * author: yanpenglei
 * Date: 2017/10/13 15:01
 */
public class CacheUtils {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static CacheUtils cacheUtils;

    @PostConstruct
    public void init() {
        cacheUtils = this;
        cacheUtils.redisTemplate = this.redisTemplate;
    }

    /**
     * 将数据存入缓存
     *
     * @param key
     * @param val
     * @return
     */
    public static void saveString(String key, String val) {

        ValueOperations<String, String> vo = cacheUtils.redisTemplate.opsForValue();
        vo.set(key, val);
    }

    /**
     * 将数据存入缓存的集合中
     *
     * @param key
     * @param val
     * @return
     */
    public static void saveToSet(String key, String val) {

        SetOperations<String, String> so = cacheUtils.redisTemplate.opsForSet();

        so.add(key, val);
    }

    /**
     * key 缓存Key
     * @param key
     * @return
     */
    public static String getFromSet(String key) {
        return cacheUtils.redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET if
     * Not eXists』(如果不存在，则 SET)的简写。 <br>
     * 保存成功，返回 true <br>
     * 保存失败，返回 false
     */
    public static boolean saveNX(String key, String val) {

        /** 设置成功，返回 1 设置失败，返回 0 **/
        return cacheUtils.redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            return connection.setNX(key.getBytes(), val.getBytes());
        });

    }

    /**
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET if
     * Not eXists』(如果不存在，则 SET)的简写。 <br>
     * 保存成功，返回 true <br>
     * 保存失败，返回 false
     *
     * @param key
     * @param val
     * @param expire 超时时间
     * @return 保存成功，返回 true 否则返回 false
     */
    public static boolean saveNX(String key, String val, int expire) {

        boolean ret = saveNX(key, val);
        if (ret) {
            cacheUtils.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return ret;
    }

    /**
     * 将数据存入缓存（并设置失效时间）
     *
     * @param key
     * @param val
     * @param seconds
     * @return
     */
    public static void saveString(String key, String val, int seconds) {

        cacheUtils.redisTemplate.opsForValue().set(key, val, seconds, TimeUnit.SECONDS);
    }

    /**
     * 将自增变量存入缓存
     */
    public static void saveSeq(String key, long seqNo) {

        cacheUtils.redisTemplate.delete(key);
        cacheUtils.redisTemplate.opsForValue().increment(key, seqNo);
    }

    /**
     * 将递增浮点数存入缓存
     */
    public static void saveFloat(String key, float data) {

        cacheUtils.redisTemplate.delete(key);
        cacheUtils.redisTemplate.opsForValue().increment(key, data);
    }

    /**
     * 保存复杂类型数据到缓存
     *
     * @param key
     * @param obj
     * @return
     */
    public static void saveBean(String key, Object obj) {

        cacheUtils.redisTemplate.opsForValue().set(key, JSON.toJSONString(obj));
    }

    /**
     * 保存复杂类型数据到缓存（并设置失效时间）
     *
     * @param key
     * @param obj
     * @param seconds
     */
    public static void saveBean(String key, Object obj, int seconds) {

        cacheUtils.redisTemplate.opsForValue().set(key, JSON.toJSONString(obj), seconds, TimeUnit.SECONDS);
    }

    /**
     * 存到指定的队列中
     * @param key
     * @param val
     * @param size
     */
    public static void saveToQueue(String key, String val, long size) {

        ListOperations<String, String> lo = cacheUtils.redisTemplate.opsForList();

        if (size > 0 && lo.size(key) >= size) {
            lo.rightPop(key);
        }
        lo.leftPush(key, val);
    }

    /**
     * 保存到hash集合中
     *
     * @param hName 集合名
     * @param key
     * @param value
     */
    public static void hashSet(String hName, String key, String value) {

        cacheUtils.redisTemplate.opsForHash().put(hName, key, value);
    }

    /**
     * 根据key获取所以值
     *
     * @param key
     * @return
     */
    public static Map<Object, Object> hgetAll(String key) {

        return cacheUtils.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 保存到hash集合中
     *
     * @param <T>
     * @param hName 集合名
     * @param key
     * @param t
     */
    public static <T> void hashSet(String hName, String key, T t) {

        hashSet(hName, key, JSON.toJSONString(t));
    }

    /**
     * 取得复杂JSON数据
     *
     * @param key
     * @param clazz
     * @param clazz
     * @return
     */
    public static <T> T getBean(String key, Class<T> clazz) {

        String value = cacheUtils.redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    /**
     * 从缓存中取得字符串数据
     *
     * @param key
     * @return 数据
     */
    public static String getString(String key) {
        cacheUtils.redisTemplate.opsForValue().get(key);

        return cacheUtils.redisTemplate.opsForValue().get(key);
    }

    /**
     * 从指定队列里取得数据
     * @param key
     * @param size
     * @return
     */
    public static List<String> getFromQueue(String key, long size) {

        boolean flag = cacheUtils.redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            return connection.exists(key.getBytes());
        });

        if (flag) {
            return new ArrayList<>();
        }
        ListOperations<String, String> lo = cacheUtils.redisTemplate.opsForList();
        if (size > 0) {
            return lo.range(key, 0, size - 1);
        } else {
            return lo.range(key, 0, lo.size(key) - 1);
        }
    }

    /**
     * 从指定队列里取得数据
     * @param key
     * @return
     */
    public static String popQueue(String key) {

        return cacheUtils.redisTemplate.opsForList().rightPop(key);

    }

    /**
     * 取得序列值的下一个
     *
     * @param key
     * @return
     */
    public static Long getSeqNext(String key) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Long>) connection -> {

            return connection.incr(key.getBytes());

        });
    }

    /**
     * 取得序列值的下一个
     *
     * @param key
     * @return
     */
    public static Long getSeqNext(String key, long value) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Long>) connection -> {

            return connection.incrBy(key.getBytes(), value);

        });

    }

    /**
     * 将序列值回退一个
     *
     * @param key
     * @return
     */
    public static void getSeqBack(String key) {

        cacheUtils.redisTemplate.execute((RedisCallback<Long>) connection -> connection.decr(key.getBytes()));

    }

    /**
     * 从hash集合里取得
     *
     * @param hName
     * @param key
     * @return
     */
    public static Object hashGet(String hName, String key) {

        return cacheUtils.redisTemplate.opsForHash().get(hName, key);
    }

    public static <T> T hashGet(String hName, String key, Class<T> clazz) {

        return JSON.parseObject((String) hashGet(hName, key), clazz);
    }

    /**
     * 增加浮点数的值
     *
     * @param key
     * @return
     */
    public static Double incrFloat(String key, double incrBy) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Double>) connection -> {

            return connection.incrBy(key.getBytes(), incrBy);

        });
    }

    /**
     * 判断是否缓存了数据
     *
     * @param key 数据KEY
     * @return 判断是否缓存了
     */
    public static boolean isCached(String key) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            return connection.exists(key.getBytes());
        });
    }

    /**
     * 判断hash集合中是否缓存了数据
     *
     * @param hName
     * @param key   数据KEY
     * @return 判断是否缓存了
     */
    public static boolean hashCached(String hName, String key) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            return connection.hExists(key.getBytes(), key.getBytes());
        });
    }

    /**
     * 判断是否缓存在指定的集合中
     *
     * @param key 数据KEY
     * @param val 数据
     * @return 判断是否缓存了
     */
    public static boolean isMember(String key, String val) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            return connection.sIsMember(key.getBytes(), val.getBytes());
        });
    }

    /**
     * 从缓存中删除数据
     *
     * @param key
     * @return
     */
    public static void delKey(String key) {

        cacheUtils.redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(key.getBytes()));
    }

    /**
     * 设置超时时间
     *
     * @param key
     * @param seconds
     */
    public static void expire(String key, int seconds) {
        cacheUtils.redisTemplate
                .execute((RedisCallback<Boolean>) connection -> connection.expire(key.getBytes(), seconds));

    }

    /**
     * 列出set中所有成员
     *
     * @param setName set名
     * @return
     */
    public static Set<Object> listSet(String setName) {

        return cacheUtils.redisTemplate.opsForHash().keys(setName);

    }

    /**
     * 向set中追加一个值
     *
     * @param setName set名
     * @param value
     */
    public static void setSave(String setName, String value) {

        cacheUtils.redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.sAdd(setName.getBytes(), value.getBytes()));

    }

    /**
     * 逆序列出sorted set包括分数的set列表
     *
     * @param key   set名
     * @param start 开始位置
     * @param end   结束位置
     * @return 列表
     */
    public static Set<RedisZSetCommands.Tuple> listSortedsetRev(String key, int start, int end) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Set<RedisZSetCommands.Tuple>>) connection -> {
            return connection.zRevRangeWithScores(key.getBytes(), start, end);
        });
    }

    /**
     * 逆序取得sorted sort排名
     *
     * @param key    set名
     * @param member 成员名
     * @return 排名
     */
    public static Long getRankRev(String key, String member) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Long>) connection -> {
            return connection.zRevRank(key.getBytes(), member.getBytes());
        });

    }

    /**
     * 根据成员名取得sorted sort分数
     *
     * @param key    set名
     * @param member 成员名
     * @return 分数
     */
    public static Double getMemberScore(String key, String member) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Double>) connection -> {
            return connection.zScore(key.getBytes(), member.getBytes());
        });
    }

    /**
     * 向sorted set中追加一个值
     *
     * @param key    set名
     * @param score  分数
     * @param member 成员名称
     */
    public static void saveToSortedset(String key, Double score, String member) {

        cacheUtils.redisTemplate.execute(
                (RedisCallback<Boolean>) connection -> connection.zAdd(key.getBytes(), score, member.getBytes()));
    }

    /**
     * 从sorted set删除一个值
     *
     * @param key    set名
     * @param member 成员名称
     */
    public static void delFromSortedset(String key, String member) {
        cacheUtils.redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.zRem(key.getBytes(), member.getBytes()));

    }

    /**
     * 从hash map中取得复杂JSON数据
     *
     * @param key
     * @param field
     * @param clazz
     */
    public static <T> T getBeanFromMap(String key, String field, Class<T> clazz) {

        byte[] input = cacheUtils.redisTemplate.execute((RedisCallback<byte[]>) connection -> {
            return connection.hGet(key.getBytes(), field.getBytes());
        });
        return JSON.parseObject(input, clazz, Feature.AutoCloseSource);
    }

    /**
     * 从hashmap中删除一个值
     *
     * @param key   map名
     * @param field 成员名称
     */
    public static void delFromMap(String key, String field) {

        cacheUtils.redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.hDel(key.getBytes(), field.getBytes()));

    }

    /**
     * @param key
     * @return
     * @Description: 根据key增长 ，计数器
     * @author clg
     * @date 2016年6月30日 下午2:37:52
     */
    public static long incr(String key) {

        return cacheUtils.redisTemplate.execute((RedisCallback<Long>) connection -> {
            return connection.incr(key.getBytes());
        });
    }


    /**
     * 根据key获取当前计数结果
     * @param key
     * @return
     */
    public static String getCount(String key) {

        return cacheUtils.redisTemplate.opsForValue().get(key);
    }

    /**
     * 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表
     *
     * @param <T>
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lpush(String key, T value) {

        return cacheUtils.redisTemplate.opsForList().leftPush(key, JSON.toJSONString(value));
    }

    /**
     * 只有当 key 已经存在并且存着一个 list 的时候，在这个 key 下面的 list 的头部插入 value。 与 LPUSH 相反，当 key
     * 不存在的时候不会进行任何操作
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lpushx(String key, T value) {

        return cacheUtils.redisTemplate.opsForList().leftPushIfPresent(key, JSON.toJSONString(value));
    }

    /**
     * 返回存储在 key 里的list的长度。 如果 key 不存在，那么就被看作是空list，并且返回长度为 0
     *
     * @param key
     * @return
     */
    public static Long llen(String key) {

        return cacheUtils.redisTemplate.opsForList().size(key);
    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素。 start 和 end
     * 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     *
     * @param key
     * @return
     */
    public static List<String> lrange(String key, long start, long end) {

        return cacheUtils.redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 移除并且返回 key 对应的 list 的第一个元素
     *
     * @param key
     * @return
     */
    public static String lpop(String key) {

        return cacheUtils.redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 保存到hash集合中 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key
     * 关联。如果字段已存在，该操作无效果。
     *
     * @param hName 集合名
     * @param key
     * @param value
     */
    public static void hsetnx(String hName, String key, String value) {

        cacheUtils.redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.hSetNX(key.getBytes(),
                key.getBytes(), value.getBytes()));

    }

    /**
     * 保存到hash集合中 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key
     * 关联。如果字段已存在，该操作无效果。
     *
     * @param hName 集合名
     * @param key
     * @param t
     * @param <T>
     */
    public static <T> void hsetnx(String hName, String key, T t) {
        hsetnx(hName, key, JSON.toJSONString(t));
    }

}
