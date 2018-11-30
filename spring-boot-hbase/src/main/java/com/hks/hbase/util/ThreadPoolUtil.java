package com.hks.hbase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程池工具类
 * Created by babylon on 2016/12/4.
 */
public class ThreadPoolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);

    //线程池的基础参数 实际使用可写入到配置文件中
    private static ThreadPoolUtil threadPool;
    private ThreadPoolExecutor executor = null;
    // 核心池的大小 运行线程的最大值 当线程池中的线程数目达到corePoolSize后，就会把多余的任务放到缓存队列当中；
    private int corePoolSize = 10;
    // 创建线程最大值
    private int maximumPoolSize = 15;
    // 线程没有执行任务时 被保留的最长时间 超过这个时间就会被销毁 直到线程数等于 corePoolSize
    private long keepAliveTime = 1;
    // 等待线程池任务执行结束超时时间
    private long timeout = 10;

    /**
     * 参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性：
     * TimeUnit.DAYS;               天
     * TimeUnit.HOURS;             小时
     * TimeUnit.MINUTES;           分钟
     * TimeUnit.SECONDS;           秒
     * TimeUnit.MILLISECONDS;      毫秒
     * TimeUnit.MICROSECONDS;      微妙
     * TimeUnit.NANOSECONDS;       纳秒
     ***/
    private TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 用来储存等待中的任务的容器
     * <p>
     * 几种选择：
     * ArrayBlockingQueue;
     * LinkedBlockingQueue;
     * SynchronousQueue;
     * 区别太罗嗦请百度  http://blog.csdn.net/mn11201117/article/details/8671497
     */
    private LinkedBlockingQueue workQueue = new LinkedBlockingQueue<Runnable>();

    /**
     * 双重校验锁
     *
     * @return
     */
    public static ThreadPoolUtil init() {
        if (threadPool == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {//2
                    threadPool = new ThreadPoolUtil();
                }
            }
        }
        return threadPool;
    }

    /**
     * 私有构造方法
     */
    private ThreadPoolUtil() {
        //实现线程池
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);
        System.out.println("线程池初始化成功");
    }

    /**
     * 线程池获取方法
     *
     * @return
     */
    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    /**
     * 准备执行 抛入线程池
     *
     * @param t
     */
    public void execute(Thread t) {
        executor.execute(t);
    }

    public void execute(Runnable t) {
        executor.execute(t);
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }

    /**
     * 异步提交返回 Future
     * Future.get()可获得返回结果
     *
     * @return
     */
    public Future<?> submit(Runnable t) {
        return executor.submit(t);
    }

    /**
     * 异步提交返回 Future
     * Future.get()可获得返回结果
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Future<?> submit(Callable t) {
        return getExecutor().submit(t);
    }

    /**
     * 销毁线程池 已入池的任务会继续执行
     */
    public void shutdown() {
        getExecutor().shutdown();
    }

    /**
     * 销毁线程池 终止池内所有任务
     */
    public void shutdownNow() {
        getExecutor().shutdownNow();
    }

    /**
     * 阻塞，直到线程池里所有任务结束
     */
    public void awaitTermination() throws InterruptedException {
        logger.info("Thread pool ,awaitTermination started, please wait till all the jobs complete.");
        executor.awaitTermination(timeout, unit);
    }

    /**
     * 阻塞，直到线程池里所有任务结束
     *
     * @param timeout 等待超时时间
     * @throws InterruptedException
     */
    public void awaitTermination(int timeout) throws InterruptedException {
        logger.info("Thread pool ,awaitTermination started, please wait till all the jobs complete.");
        executor.awaitTermination(timeout, unit);
    }

}
