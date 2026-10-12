package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.entity.ChatSession;
import com.simulation.simulation_backend.service.ChatSessionService;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/chat-sessions")
public class ChatSessionController {

    private final ChatSessionService chatSessionService;

    public ChatSessionController(ChatSessionService chatSessionService){
        this.chatSessionService = chatSessionService;
    }

    @PostMapping
    ResponseEntity<ChatSession> crateChatSession(@RequestBody ChatSession chatSession){
        ChatSession createdChatSession = chatSessionService.createChatSession(chatSession);
        return ResponseEntity.ok(createdChatSession);
    }
    @GetMapping
    public ResponseEntity<List<ChatSession>> getAllChatSession(){
        return ResponseEntity.ok(chatSessionService.getAllChatSessions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChatSession> getChatSessionById(@PathVariable Long id){
        return chatSessionService.getChatSessionById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }
    @PutMapping("/{id}")
    public ResponseEntity<ChatSession> updateChatSession(@PathVariable Long id, @RequestBody ChatSession chatSession) {
        return ResponseEntity.ok(chatSessionService.updateChatSession(id, chatSession));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatSession(@PathVariable Long id) {

        chatSessionService.deleteChatSession(id);

        return ResponseEntity.noContent().build();
    }
}
