package com.heks.springbootollama.view;

import com.heks.springbootollama.service.ChatService;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.firitin.components.messagelist.MarkdownMessage;
import org.vaadin.firitin.components.messagelist.MarkdownMessage.Color;

import java.util.Optional;

@Route("")
public class MyDeveloperAssistanceView extends VerticalLayout {

    public MyDeveloperAssistanceView(ChatService chatService) {
        String chatId = chatService.establishChat();
        var messageList = new VerticalLayout();
        var messageInput = new MessageInput();
        messageInput.setWidthFull();
        messageInput.addSubmitListener(e -> submitPromptListener(chatId, e, chatService, messageList));
        addClassName("centered-content");
        add(messageList, messageInput);
    }

    private static void submitPromptListener(String chatId, MessageInput.SubmitEvent e, ChatService chatService, VerticalLayout messageList) {
        var question = e.getValue();
        var userMessage = new MarkdownMessage(question, "You", Color.AVATAR_PRESETS[6]);
        var assistantMessage = new MarkdownMessage("haiyangAI", Color.AVATAR_PRESETS[0]);
        messageList.add(userMessage, assistantMessage);
        chatService.chatV2(chatId, question)
                .map(res -> Optional.ofNullable(res.getResult().getOutput().getText()).orElse(""))
                .subscribe(assistantMessage::appendMarkdownAsync);
    }
}