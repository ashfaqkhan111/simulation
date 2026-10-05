package com.simulation.simulation_backend.repository;

import com.simulation.simulation_backend.entity.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepositroy extends JpaRepository <ChatMessages, Long>{
    List<ChatMessages> findByChatSession(Long chatSessoinId);
    
}
