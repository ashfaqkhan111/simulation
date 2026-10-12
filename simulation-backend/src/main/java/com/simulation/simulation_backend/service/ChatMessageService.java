package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.ChatMessage;
import com.simulation.simulation_backend.repository.ChatMessageRepositroy;
import com.simulation.simulation_backend.repository.ChatSessionRepository;
import com.simulation.simulation_backend.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // public ChatMessage updateChatMessage(ChatMessage chatMessage){
    //     return chatMessageRepositroy.save(chatMessage);
    // }

    // public void deleteChatMessage(Long id){
    //     chatMessageRepositroy.deleteById(id);
    // }
    
    @Transactional 
    public ChatMessage updateChatMessage(Long id, ChatMessage incoming){
        ChatMessage existing = chatMessageRepositroy.findById(id).orElseThrow(() -> 
        new ResourceNotFoundException("chat message "+id+" not found"));

        existing.setMessage(incoming.getMessage());
        existing.setSender(incoming.getSender());

        return chatMessageRepositroy.save(existing);
    }

    @Transactional 
    public void deleteChatMessage(Long id){
        if(!chatMessageRepositroy.existsById(id)){
            throw new ResourceNotFoundException("chat session "+id+" not found");
        }

        chatMessageRepositroy.deleteById(id);
    }
}
