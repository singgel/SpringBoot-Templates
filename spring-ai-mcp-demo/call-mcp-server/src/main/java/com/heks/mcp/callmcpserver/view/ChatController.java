package com.heks.mcp.callmcpserver.view;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import io.modelcontextprotocol.client.McpSyncClient;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.mcp.SyncMcpToolCallback;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author singgel
 * 2025/03/18/下午8:00
 */
@RestController
@RequestMapping("/dashscope/chat-client")
public class ChatController {

    private final ChatClient chatClient;

    private final ChatMemory chatMemory = new InMemoryChatMemory();


    public ChatController(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients, ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultOptions(DashScopeChatOptions.builder().withTopP(0.7).build())
                .build();
    }



    @RequestMapping(value = "/generate_stream", method = RequestMethod.GET)
    public Flux<ChatResponse> generateStream(HttpServletResponse response, @RequestParam("id") String id, @RequestParam("prompt") String prompt) {
        response.setCharacterEncoding("UTF-8");
        var messageChatMemoryAdvisor = new MessageChatMemoryAdvisor(chatMemory, id, 10);
        return this.chatClient.prompt(prompt)
                .advisors(messageChatMemoryAdvisor)
                .stream()
                .chatResponse();
    }


    @GetMapping("/advisor/chat/{id}/{prompt}")
    public Flux<String> advisorChat(
            HttpServletResponse response,
            @PathVariable String id,
            @PathVariable String prompt) {

        response.setCharacterEncoding("UTF-8");
        var messageChatMemoryAdvisor = new MessageChatMemoryAdvisor(chatMemory, id, 10);
        return this.chatClient.prompt(prompt)
                .advisors(messageChatMemoryAdvisor).stream().content();
    }



}
