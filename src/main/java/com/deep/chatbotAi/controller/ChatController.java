package com.deep.chatbotAi.controller;

import com.deep.chatbotAi.dto.ChatRequest;
import com.deep.chatbotAi.dto.ChatResponse;
import com.deep.chatbotAi.service.ChatServiceInte;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceInte chatService;

    @GetMapping("/")
    public String showChatPage() {
        return "chat";
    }

    @PostMapping("/api/chat")
    @ResponseBody
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        System.out.println("USER MSG: " + request.getMessage()); //
        return ResponseEntity.ok(chatService.getReply(request));
    }

}


