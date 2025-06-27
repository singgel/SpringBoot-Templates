package com.heks.springbootollama.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AIController {

    private final OllamaChatModel chatModel;
    private final VectorStore vectorStore;

    @Autowired
    public AIController(OllamaChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/ai")
    public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    @GetMapping("/ai/vectorStoreSearch")
    public Map<String, String> getSystem(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        List<Document> similarDocuments = this.vectorStore.similaritySearch(message);
        String documents = similarDocuments.stream().map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
        return Map.of("result", documents);
    }

    @GetMapping(value = "/ai/generateStream2", produces = MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
    public Flux<ServerSentEvent<String>> generateStream2(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        String promptContext = """
                你是一个信息查询助手，你的名字为"haiyangAI"。您的回复将简短明了，但仍然具有信息量和帮助性。
                今天日期是{current_date}。
                参考信息：{question_answer_context}
                """;
        return ChatClient.create(chatModel)
                .prompt()
                .user(message)
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(2).build(), promptContext))
                .user(b -> b.param("current_date", LocalDate.now().toString()))
                .stream()
                .content()
                .map(resp -> ServerSentEvent.builder(resp)
                        .event("generation").build());
    }

}
