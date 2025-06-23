package com.heks.mcp.mcpserver;


import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;

class McpServerApplicationTests {

    public static void main(String[] args) {
        var transport = new HttpClientSseClientTransport("http://localhost:8080");
        new SampleClient(transport).run();
    }

}
