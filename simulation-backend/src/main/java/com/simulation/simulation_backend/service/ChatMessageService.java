package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.ChatMessage;
import com.simulation.simulation_backend.repository.ChatMessageRepositroy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    private final ChatMessageRepositroy chatMessageRepositroy;

    public ChatMessageService(ChatMessageRepositroy chatMessageRepositroy) {
        this.chatMessageRepositroy = chatMessageRepositroy;
    }
    
    public ChatMessage createChatMessage(ChatMessage chatMessage){
        return chatMessageRepositroy.save(chatMessage);
    }

    public List<ChatMessage> getAllChatMessages(){
        return chatMessageRepositroy.findAll();
    }

    public Optional<ChatMessage> getChatMessageById(Long id){
        return chatMessageRepositroy.findById(id);
    }

    public ChatMessage updateChatMessage(ChatMessage chatMessage){
        return chatMessageRepositroy.save(chatMessage);
    }

    public void deleteChatMessage(Long id){
        chatMessageRepositroy.deleteById(id);
    }
    
}
