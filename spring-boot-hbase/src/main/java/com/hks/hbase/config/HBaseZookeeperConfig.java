package com.hks.hbase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:zookeeper.properties"),
        @PropertySource(value = "${zookeeper.properties}", ignoreResourceNotFound = true)
})
public class HBaseZookeeperConfig {
    public String getQuorum() {
        return quorum;
    }

    public String getPort() {
        return port;
    }

    public String getZnodeParent() {
        return znodeParent;
    }

    public String getPause() {
        return pause;
    }

    public String getRetriesNumber() {
        return retriesNumber;
    }

    public String getRpcTimeOut() {
        return rpcTimeOut;
    }

    public String getOperationTimeout() {
        return operationTimeout;
    }

    public String getScannerTimeout() {
        return scannerTimeout;
    }

    @Value("${hbase.zookeeper.quorum}")
    private String quorum;
    @Value("${hbase.zookeeper.property.clientPort}")
    private String port;
    @Value("${zookeeper.znode.parent}")
    private String znodeParent;
    @Value("${hbase.client.pause}")
    private String pause;
    @Value("${hbase.client.retries.number}")
    private String retriesNumber;
    @Value("${hbase.rpc.timeout}")
    private String rpcTimeOut;
    @Value("${hbase.client.operation.timeout}")
    private String operationTimeout;

    @Value("${hbase.client.scanner.timeout.period}")
    private String scannerTimeout;

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setZnodeParent(String znodeParent) {
        this.znodeParent = znodeParent;
    }

    public void setPause(String pause) {
        this.pause = pause;
    }

    public void setRetriesNumber(String retriesNumber) {
        this.retriesNumber = retriesNumber;
    }

    public void setRpcTimeOut(String rpcTimeOut) {
        this.rpcTimeOut = rpcTimeOut;
    }

    public void setOperationTimeout(String operationTimeout) {
        this.operationTimeout = operationTimeout;
    }

    public void setScannerTimeout(String scannerTimeout) {
        this.scannerTimeout = scannerTimeout;
    }
}