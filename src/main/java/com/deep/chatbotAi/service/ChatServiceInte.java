package com.deep.chatbotAi.service;

import com.deep.chatbotAi.dto.ChatRequest;
import com.deep.chatbotAi.dto.ChatResponse;

public interface ChatServiceInte {
    ChatResponse getReply(ChatRequest request);
}
