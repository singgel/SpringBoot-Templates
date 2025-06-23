package com.heks.mcp.callmcpserver.cfg;

import io.modelcontextprotocol.client.McpClient;
import org.springframework.ai.mcp.customizer.McpSyncClientCustomizer;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author singgel
 * 2025/03/18/下午8:02
 */
@Configuration
public class McpClientCfg implements McpSyncClientCustomizer {


    @Override
    public void customize(String name, McpClient.SyncSpec spec) {
        // do nothing
        spec.requestTimeout(Duration.ofSeconds(30));
    }
}
