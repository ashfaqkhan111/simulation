package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.ChatSession;
import com.simulation.simulation_backend.repository.ChatMessageRepositroy;
import com.simulation.simulation_backend.repository.ChatSessionrepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service 
public class ChatSessionService {
    
    private final ChatSessionrepository chatSessionRepository;

    public ChatSessionService(ChatSessionrepository chatSessionRepository){
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

    public ChatSession updateChatSession(ChatSession chatSession){
        return chatSessionRepository.save(chatSession);
    }

    public void deleteChatSession(Long id){
        chatSessionRepository.deleteById(id);
    }
}
