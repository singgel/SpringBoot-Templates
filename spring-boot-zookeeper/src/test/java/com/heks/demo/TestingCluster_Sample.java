package com.heks.demo;

import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestingCluster_Sample {
    TestingCluster cluster;

    @Before
    public void setup() throws Exception {
        List<InstanceSpec> specs = new ArrayList<>();
        List<Integer> ports = new ArrayList<>();
        int port = 30155, electionPort = 31155, quorumPort = 32155;
        for (int i = 0; i < 3; i++) {
            InstanceSpec spec = new InstanceSpec(null, port, electionPort, quorumPort,
                    true, i, 10000, 100, null, "127.0.0.1");
            specs.add(spec);
            ports.add(port);
            port++;
            electionPort++;
            quorumPort++;
        }
        cluster = new TestingCluster(specs);
        try {
            cluster.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    cluster.stop();
                } catch (IOException e) {
                }
            }));
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void TestCluster() throws Exception {
        Thread.sleep(2000);
        TestingZooKeeperServer leader = null;
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            System.out.print(zs.getInstanceSpec().getServerId() + "-");
            System.out.print(zs.getQuorumPeer().getServerState() + "-");
            //System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
            System.out.println(zs.getInstanceSpec().getConnectString());
            if (zs.getQuorumPeer().getServerState().equals("leading")) {
                leader = zs;
            }
        }
        leader.kill();
        Thread.sleep(2000);
        System.out.println("--After leader kill:");
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            System.out.print(zs.getInstanceSpec().getServerId() + "-");
            System.out.print(zs.getQuorumPeer().getServerState() + "-");
            //System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
            System.out.println(zs.getInstanceSpec().getConnectString());
        }
    }

    @After
    public void teardown() throws IOException {
        cluster.stop();
    }

    public static void main(String[] args) throws Exception {
        TestingCluster cluster = new TestingCluster(3);
        cluster.start();
        Thread.sleep(2000);

        TestingZooKeeperServer leader = null;
        for(TestingZooKeeperServer zs : cluster.getServers()){
            System.out.print(zs.getInstanceSpec().getServerId()+"-");
            System.out.print(zs.getQuorumPeer().getServerState()+"-");
            System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
            if( zs.getQuorumPeer().getServerState().equals( "leading" )){
                leader = zs;
            }
        }
        leader.kill();
        System.out.println( "--After leader kill:" );
        for(TestingZooKeeperServer zs : cluster.getServers()){
            System.out.print(zs.getInstanceSpec().getServerId()+"-");
            System.out.print(zs.getQuorumPeer().getServerState()+"-");
            System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }
        cluster.stop();
    }

}
