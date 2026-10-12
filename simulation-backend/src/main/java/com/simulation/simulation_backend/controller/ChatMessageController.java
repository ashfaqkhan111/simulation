package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.entity.ChatMessage;
import com.simulation.simulation_backend.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/api/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

  
    @PostMapping
    public ResponseEntity<ChatMessage> createChatMessage(
            @RequestBody ChatMessage chatMessage) {

        ChatMessage createdMessage = chatMessageService.createChatMessage(chatMessage);

        return ResponseEntity.ok(createdMessage);
    }

   
    @GetMapping
    public ResponseEntity<List<ChatMessage>> getAllChatMessages() {

        return ResponseEntity.ok(chatMessageService.getAllChatMessages());
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<ChatMessage> getChatMessageById(
            @PathVariable Long id) {

        return chatMessageService.getChatMessageById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<ChatMessage> updateChatMessage(@PathVariable Long id, @RequestBody ChatMessage chatMessage) {

        return ResponseEntity.ok(chatMessageService.updateChatMessage(id,chatMessage));
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<ChatMessageService> deleteChatMessage(@PathVariable Long id) {

        chatMessageService.deleteChatMessage(id);

        return ResponseEntity.noContent().build();
    }
}
    

