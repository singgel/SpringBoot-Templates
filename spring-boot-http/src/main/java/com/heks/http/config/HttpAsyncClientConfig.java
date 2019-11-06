package com.heks.http.config;

import com.google.common.base.Throwables;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class HttpAsyncClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpAsyncClientConfig.class);

    @Resource
    private HttpClientProperties p;

    @Bean
    public PoolingNHttpClientConnectionManager poolingConnectionManager() {
        try {
            PoolingNHttpClientConnectionManager poolingConnectionManager = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(IOReactorConfig.DEFAULT));
            //最大连接数
            poolingConnectionManager.setMaxTotal(p.getMaxTotalConnections());
            //同路由并发数
            poolingConnectionManager.setDefaultMaxPerRoute(p.getDefaultMaxPerRoute());
            return poolingConnectionManager;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Bean
    public CloseableHttpAsyncClient httpClient(final PoolingNHttpClientConnectionManager connectionManager) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(p.getRequestTimeout())
                .setConnectTimeout(p.getConnectTimeout())
                .setSocketTimeout(p.getSocketTimeout()).build();

        return HttpAsyncClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }

    private ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext httpContext) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return p.getDefaultKeepAliveTimeMillis();
            }
        };
    }

    @Bean
    public Runnable idleConnectionMonitor(final PoolingNHttpClientConnectionManager nHttpClientConnectionManager) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 10000)
            public void run() {
                try {
                    if (nHttpClientConnectionManager != null) {
                        LOGGER.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
                        nHttpClientConnectionManager.closeExpiredConnections();
                        nHttpClientConnectionManager.closeIdleConnections(p.getCloseIdleConnectionWaitTimeSecs(), TimeUnit.SECONDS);
                    } else {
                        LOGGER.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
                    }
                } catch (Exception e) {
                    LOGGER.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
                }
            }
        };
    }
}
