package com.hks.netty;

import java.util.concurrent.*;

public class NettyTest {

    public static void main(String[] args) {

        //实现一个Callable接口
        Callable<Netty> c = new Callable<Netty>() {

            @Override
            public Netty call() throws Exception {

                //这里是你的业务逻辑处理
                System.out.println("子-线任务执行:"+Thread.currentThread().getId());

                //让当前线程阻塞1秒看下效果
                Thread.sleep(1000);
                System.out.println("子--线任务执行:"+Thread.currentThread().getId());
                return new Netty("张三");
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(2);

        //记得要用submit，执行Callable对象
        Future<Netty> fn = es.submit(c);
        //一定要调用这个方法，不然executorService.isTerminated()永远不为true
        es.shutdown();

        //无限循环等待任务处理完毕  如果已经处理完毕 isDone返回true
        while (!fn.isDone()) {
            try {
                //处理完毕后返回的结果
                Netty nt = fn.get();
                System.out.println(nt.name);
                System.out.println("子---线任务执行:"+Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    static class Netty {

        private String name;

        private Netty(String name) {
            this.name = name;
        }
    }
}
