package com.heks.mcp.mcpclient.cfg;

import org.springframework.ai.mcp.client.McpClient;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.client.transport.ServerParameters;
import org.springframework.ai.mcp.client.transport.StdioClientTransport;
import org.springframework.ai.mcp.spring.McpFunctionCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author singgel
 * 2025/03/12/下午2:20
 */
@Configuration
public class McpConfig {

    @Bean
    public List<McpFunctionCallback> functionCallbacks(List<McpSyncClient> mcpSyncClients) {
        List<McpFunctionCallback> list = new ArrayList<>();

        for (McpSyncClient mcpSyncClient : mcpSyncClients) {
            list.addAll(mcpSyncClient.listTools(null)
                    .tools()
                    .stream()
                    .map(tool -> new McpFunctionCallback(mcpSyncClient, tool))
                    .toList());
        }
        return list;
    }

    @Bean(destroyMethod = "close")
    public McpSyncClient mcpFileSysClient() {
        // 把这里的路径记得改为自己的真实路径
        var stdioParams = ServerParameters.builder("D:\\software\\nodeJs\\npx.cmd")
                .args("-y", "@modelcontextprotocol/server-filesystem", "D:\\工作日志")
                .build();
        var mcpClient = McpClient.using(new StdioClientTransport(stdioParams))
                .requestTimeout(Duration.ofSeconds(10)).sync();
        var init = mcpClient.initialize();

        System.out.println("mcpFileSysClient loading init=" + init);
        return mcpClient;
    }

//    @Bean(destroyMethod = "close")
//    public McpSyncClient mcpDbClient() {
//        // 把这里的路径记得改为自己的真实路径
//        var stdioParams = ServerParameters.builder("D:\\Program Files\\python3.12.3\\Scripts\\uvx.exe")
//                .args("mcp-server-sqlite", "--db-path", "D:\\work-space-study\\spring-ai-mcp-demo\\mcp-client\\src\\main\\resources\\test.db")
//                .build();
//        var mcpClient = McpClient.using(new StdioClientTransport(stdioParams))
//                .requestTimeout(Duration.ofSeconds(10)).sync();
//        var init = mcpClient.initialize();
//        System.out.println("mcpDbClient loading init=" + init);
//        return mcpClient;
//
//    }



}
