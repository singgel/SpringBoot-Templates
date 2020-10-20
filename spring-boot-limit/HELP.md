# 限流算法
## 漏桶算法
漏桶算法思路很简单，水（也就是请求）先进入到漏桶里，漏桶以一定的速度出水，当水流入速度过大会直接溢出，然后就拒绝请求，可以看出漏桶算法能强行限制数据的传输速率。 示意图（来源网络）如下：  
[pic]  
## 令牌桶算法
令牌桶算法和漏桶算法效果一样但方向相反的算法，更加容易理解。随着时间流逝，系统会按恒定1/QPS时间间隔（如果QPS=100，则间隔是10ms）往桶里加入令牌（想象和漏洞漏水相反，有个水龙头在不断的加水），如果桶已经满了就不再加了。新请求来临时，会各自拿走一个令牌，如果没有令牌可拿了就阻塞或者拒绝服务。示意图（来源网络）如下：  
[pic]

# 单机实现 
类似guava的ratelimit  
``` java
/**
 * 使用计数器限速，guava的RateLimiter没用过研究后再说
 * <p>
 * 单机1秒限速1000
 */
public class RateLimiterutils {
    private final static long DELTA_MILLIS = 1000;
    private final static long PERMITS_PER_SECOND = 888;
    private static int counter = 0;
    private static long timestamp = System.currentTimeMillis();

    public synchronized static void tryAcquire(int delta) {
        if (delta > PERMITS_PER_SECOND) {
            throw new IllegalArgumentException(String.format("delta gather than permitsPerSecond, delta:%d, permitsPerSecond:%d", delta, PERMITS_PER_SECOND));
        }
        long now = System.currentTimeMillis();
        if (now - timestamp < DELTA_MILLIS) {
            if (counter + delta <= PERMITS_PER_SECOND) {
                counter += delta;
            } else {
                try {
                    Thread.sleep(timestamp + PERMITS_PER_SECOND - now);
                    counter = delta;
                    timestamp = timestamp + PERMITS_PER_SECOND;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            counter = delta;
            timestamp = now;
        }
    }
}
```

# 分布式限速器
## 要解决的问题
1. 动态规则：比如限流的QPS我们希望可以动态修改，限流的功能可以随时开启、关闭，限流的规则可以跟随业务进行动态变更等。  
2. 集群限流：比如对Spring Cloud微服务架构中的某服务下的所有实例进行统一限流，以控制后续访问数据库的流量。  
3. 熔断降级：比如在调用链路中某个资源出现不稳定状态时（例如调用超时或异常比例升高），对这个资源的调用进行限制，让请求快速失败，避免影响到其它的资源而导致级联错误。  
## 实现方案
1. Redis令牌桶   
完整的工程代码实现：https://github.com/singgel/SpringBoot-Templates  
ratelimit模块  
2. 将分布式限速器写成一个grpc、http服务  
这个案例是一个项目，讲不使用redis制作分布式限速器服务，提供基础服务  
参见：https://www.mailgun.com/blog/gubernator-cloud-native-distributed-rate-limiting-microservices/  
3. QPS统一分配  
直接想法就是使用负载均衡的想法  
举个例子，我们有两台服务器实例，对应的是同一个应用程序（Application.name相同），程序中设置的QPS为100，将应用程序与同一个控制台程序进行连接，控制台端依据应用的实例数量将QPS进行均分，动态设置每个实例的QPS为50，若是遇到两个服务器的配置并不相同，在负载均衡层的就已经根据服务器的优劣对流量进行分配，例如一台分配70%流量，另一台分配30%的流量。面对这种情况，控制台也可以对其实行加权分配QPS的策略。  
4. 发票服务器  
目的是因为如果单纯的限速器会出现redis热key问题，在访问层封一层组批  
这种方案的思想是建立在Redis令牌桶方案的基础之上的。如何解决每次取令牌都伴随一次网络开销，该方案的解决方法是建立一层控制端，利用该控制端与Redis令牌桶进行交互，只有当客户端的剩余令牌数不足时，客户端才向该控制层取令牌并且每次取一批。

这种思想类似于Java集合框架的数组扩容，设置一个阈值，只有当超过该临界值时，才会触发异步调用。其余存取令牌的操作与本地限流无二。虽然该方案依旧存在误差，但误差最大也就一批次令牌数而已。

# 代码实现
### step1：定义lua脚本
lua脚本的注释不允许使用中文，这点要注意，不然报错  
令牌桶初始化，因为令牌的控制脚本需要用到前置参数    
``` lua
-- curr_timestamp: redis current timestamp(Unit of second)
redis.replicate_commands()
local curr_timestamp = tonumber(redis.call('TIME')[1])
--[[
 last_second: token bucket last update timestamp(Unit of second)
 curr_permits: token bucket last permit amount
 max_burst: token bucket max amount
--]]
local result = 1
redis.call("HMSET", KEYS[1],
    "last_second", curr_timestamp,
    "curr_permits", ARGV[1],
    "max_burst", ARGV[2])
return result
```  
令牌桶控制脚本  
``` lua
-- curr_timestamp: redis current timestamp(Unit of second)
redis.replicate_commands()
local time = redis.call('time')
local curr_timestamp = tonumber(time[1])
local curr_microseconds = tonumber(time[2])
local require_permits = tonumber(ARGV[1])
local result = {}
-- result[1]: minimum time from next refresh(Unit of microsecond)
result[1] = (1000000 - curr_microseconds) / 1000
local ratelimit_info = redis.call("HMGET", KEYS[1], "last_second", "curr_permits", "max_burst")
local last_second = tonumber(ratelimit_info[1])
local curr_permits = tonumber(ratelimit_info[2])
local max_burst = tonumber(ratelimit_info[3])
-- If the last time has passed, update the token bucket
if (curr_timestamp > last_second) then
    curr_permits = max_burst
    redis.call("HMSET", KEYS[1], "last_second", curr_timestamp)
end
-- Update the last permit amount, base on the curr_permits change
if (curr_permits > require_permits) then
    redis.call("HMSET", KEYS[1], "curr_permits", curr_permits - require_permits)
    result[2] = require_permits
    return result
end
if (curr_permits > 0) then
    redis.call("HMSET", KEYS[1], "curr_permits", 0)
end
result[2] = curr_permits
return result
```  
### step2：java的spring注入  
先初始化  
``` java
@Bean("ratelimitLua")
public DefaultRedisScript getRedisScript() {
    DefaultRedisScript redisScript = new DefaultRedisScript();
    redisScript.setLocation(new ClassPathResource("limit/ratelimit.lua"));
    redisScript.setResultType(java.util.List.class);
    return redisScript;
}

@Bean("ratelimitInitLua")
public DefaultRedisScript getInitRedisScript() {
    DefaultRedisScript redisScript = new DefaultRedisScript();
    redisScript.setLocation(new ClassPathResource("limit/ratelimitInit.lua"));
    redisScript.setResultType(java.lang.Long.class);
    return redisScript;
}
```  
再定义ratelimit方法控制入口  
``` java  
/**
 * 利用Redis进行限流，解决分布式、高TPS的问题
 */
@Service
public class RateLimitClient {
    private final static long PERMITS_PER_SECOND = 3250;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Qualifier("getRedisScript")
    @Resource
    private RedisScript<List> ratelimitLua;

    @Qualifier("getInitRedisScript")
    @Resource
    private RedisScript<Long> ratelimitInitLua;

    /**
     * 初始化令牌桶！！！
     *
     * @param key
     * @return
     */
    public Token initToken(String key) {
        Token token = Token.SUCCESS;
        if (stringRedisTemplate.hasKey(getKey(key))) {
            return token;
        }
        Long acquire = stringRedisTemplate.execute(ratelimitInitLua,
                Collections.singletonList(getKey(key)), String.valueOf(PERMITS_PER_SECOND), String.valueOf(PERMITS_PER_SECOND));
        if (acquire == 1) {
            token = Token.SUCCESS;
        } else if (acquire == 0) {
            token = Token.SUCCESS;
        } else {
            token = Token.FAILED;
        }
        return token;
    }

    /**
     * 根据请求值去令牌桶获取，之所以用Long是因为lua脚本返回值用Integer接收有问题
     * lua要不直接返回json然后转map，为了省事直接返回list
     *
     * list.get(0) 距离下一次刷新的最小时间间隔，单位：微秒
     * list.get(1) 获取到的令牌数
     *
     * @param key
     * @param permits
     * @return
     */
    public List<Long> acquireIntervalAndToken(String key, Integer permits){
        List intervalAndToken = stringRedisTemplate.execute(ratelimitLua,
                Collections.singletonList(getKey(key)), permits.toString());
        return intervalAndToken;
    }

    public String getKey(String key) {
        return Constants.RATE_LIMIT_KEY + key;
    }
}
```
### step3：业务代码中使用  
在多线程环境下验证  
``` java
@Autowired
private RateLimitClient rateLimitClient;

@Test
public void redisLuaScriptTest() throws Exception {
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
        // 令牌桶的线程安全验证
        consumerExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int requireToken = random.nextInt(1000);
                LOGGER.info("requireToken:{}", requireToken);

                List<Long> intervalAndToken = rateLimitClient.acquireIntervalAndToken(MessageProto.Platform.HUAWEI.name(), requireToken);
                LOGGER.info("requireToken:{}, acquireToken:{} ", requireToken, intervalAndToken);
            }
        });
    }
}
```

