package com.hks.pubsub;

/**
 * Message Publisher
 *
 * @author hekuangsheng@gmail.com
 * @create 2019-05-01 19:35
 **/
public interface MessagePublisher {
    /**
     * publish message
     * @param message
     */
    void publish(String message);
}
