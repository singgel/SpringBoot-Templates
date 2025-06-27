package com.heks.springbootollama.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PromptManagementService {

    private static final Logger logger = LoggerFactory.getLogger(PromptManagementService.class);

    private final Resource systemPrompt;
    private final VectorStore vectorStore;
    private final Map<String, List<Message>> chatHistoryLog;
    private final Map<String, List<Message>> messageAggregations;

    public PromptManagementService(@Value("classpath:/rag/service.st") Resource systemPrompt, VectorStore vectorStore) {
        this.systemPrompt = systemPrompt;
        this.vectorStore = vectorStore;
        this.chatHistoryLog = new ConcurrentHashMap<>();
        this.messageAggregations = new ConcurrentHashMap<>();
    }

    public void establishChat(String chatId) {
        this.chatHistoryLog.put(chatId, new ArrayList<>());
    }

    private void commitToHistoryLog(String chatId, Message message) {
        this.chatHistoryLog.computeIfAbsent(chatId, key -> new ArrayList<>()).add(message);
    }

    public Message getSystemMessage(String chatId, String message) {
        // Retrieve related documents to query
        List<Document> similarDocuments = this.vectorStore.similaritySearch(SearchRequest.builder().query(message).topK(2).build());
        String documents = similarDocuments.stream().map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        Map<String, Object> prepareHistory = Map.of(
                "documents", documents,
                "current_date", java.time.LocalDate.now()
        );
        return new SystemPromptTemplate(this.systemPrompt).createMessage(prepareHistory);
    }

    public void addMessage(String chatId, Message message) {
        String groupId = toGroupId(chatId, message);
        this.messageAggregations.computeIfAbsent(groupId, key -> new ArrayList<>()).add(message);
        String finishReason = getProperty(message, "finishReason");
        if ("STOP".equalsIgnoreCase(finishReason) || message.getMessageType() == MessageType.USER) {
            this.finalizeMessageGroup(chatId, groupId);
        }
    }

    private String toGroupId(String chatId, Message message) {
        String messageId = getProperty(message, "id");
        return chatId + ":" + messageId;
    }

    private String getProperty(Message message, String key) {
        return "";
    }

    private void finalizeMessageGroup(String chatId, String groupId) {
        List<Message> sessionMessages = this.messageAggregations.remove(groupId);

        if (!CollectionUtils.isEmpty(sessionMessages)) {
            if (sessionMessages.size() == 1) {
                this.commitToHistoryLog(chatId, sessionMessages.get(0));
            } else {
                String aggregatedContent = sessionMessages.stream()
                        .map(Message::getText)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining());
                this.commitToHistoryLog(chatId, new AssistantMessage(aggregatedContent));
            }
        } else {
            logger.warn("No active session for groupId: {}", groupId);
        }
    }

}