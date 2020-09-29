package com.heks.demo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateExecutorTest {

    private static final int MAX_WAIT_SECONDS = 120;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private TestingCluster testingCluster;

    @After
    public void after() throws IOException {
        this.logger.info("Stopping test cluster");
        this.testingCluster.stop();
        this.wait(1000);
        this.testingCluster.close();
    }

    @Before
    public void before() throws Exception {
        this.logger.info("Starting test cluster");
        this.testingCluster = new TestingCluster(3);
        this.testingCluster.start();
        this.wait(1000);
    }

    @Test
    public void stressTest() throws Exception {

        // slowly kill off all ZooKeeper instances, eventually leading to a broken ensemble
        for (InstanceSpec eachInstance : this.testingCluster.getInstances()) {
            this.wait(5000);
            this.logger.info("Killing server {}", eachInstance.getConnectString());
            this.testingCluster.killServer(eachInstance);
        }

        // slowly restart all ZooKeeper instances, eventually restoring normal operation
        for (InstanceSpec eachInstance : this.testingCluster.getInstances()) {
            this.wait(5000);
            this.logger.info("Restarting server {}", eachInstance.getConnectString());
            this.testingCluster.restartServer(eachInstance);
        }
    }

    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            this.logger.error("Failed to wait", e);
        }
    }
}
