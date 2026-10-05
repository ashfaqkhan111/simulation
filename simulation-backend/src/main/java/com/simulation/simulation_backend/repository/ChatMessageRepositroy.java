package com.simulation.simulation_backend.repository;

import com.simulation.simulation_backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepositroy extends JpaRepository <ChatMessage, Long>{
    List<ChatMessage> findByChatSession(Long chatSessoinId);
    
}
