package com.deep.chatbotAi.service;

import com.deep.chatbotAi.dto.ChatRequest;
import com.deep.chatbotAi.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatServiceInte {

    private final OpenAIService openAIService;
    private final GoogleSearchService googleSearchService;

    @Override
    public ChatResponse getReply(ChatRequest request) {
        String msg = request.getMessage();

        // 🧠 Null or empty check
        if (msg == null || msg.trim().isEmpty()) {
            return new ChatResponse("Please type something to get a response!");
        }

        msg = msg.toLowerCase().trim(); // Normalized input

        // 🔍 If user starts with "search", do Google search
        if (msg.startsWith("search ")) {
            String query = msg.substring(7); // Remove "search " prefix
            String result = googleSearchService.search(query);
            return new ChatResponse(result);
        }

        // 🤖 Else, use OpenAI for smart reply
        String aiReply = openAIService.getAIResponse(msg);
        return new ChatResponse(aiReply);
    }
}
