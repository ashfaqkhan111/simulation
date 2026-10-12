package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.ChatSession;
import com.simulation.simulation_backend.repository.ChatMessageRepositroy;
import com.simulation.simulation_backend.exception.ResourceNotFoundException;
import com.simulation.simulation_backend.repository.ChatSessionRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service 
public class ChatSessionService {
    
    private final ChatSessionRepository chatSessionRepository;

    public ChatSessionService(ChatSessionRepository chatSessionRepository){
        this.chatSessionRepository = chatSessionRepository;
    }

    public ChatSession createChatSession(ChatSession chatSession){
        return chatSessionRepository.save(chatSession);
    }

    public List<ChatSession> getAllChatSessions(){
        return chatSessionRepository.findAll();
    }

    public Optional<ChatSession> getChatSessionById(Long id){
        return chatSessionRepository.findById(id);
    }

    // public ChatSession updateChatSession(ChatSession chatSession){
    //     return chatSessionRepository.save(chatSession);
    // }

    // public void deleteChatSession(Long id){
    //     chatSessionRepository.deleteById(id);
    // }

    public ChatSession updateChatSession(Long id, ChatSession chatSession){
        ChatSession existing = chatSessionRepository.findById(id).orElseThrow(() -> 
        new ResourceNotFoundException("Chat session "+id+" not found"));

        return existing;
    }

    public void deleteChatSession(Long id){
        if(!chatSessionRepository.existsById(id)){
            throw new ResourceNotFoundException("Chat Session "+id+ "not found");
        }
        chatSessionRepository.deleteById(id);
    }


}
