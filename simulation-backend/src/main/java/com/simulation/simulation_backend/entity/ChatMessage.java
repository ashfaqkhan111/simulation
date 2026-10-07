package com.simulation.simulation_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 
@Table (name = "caht_messages")
public class ChatMessage {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn (name = "chat_session_id", nullable = false)
    private ChatSession chatSession;

    @Column (name = "sender", nullable = false)
    private String sender;

    @Column (name = "message", nullable = false)
    private String message;

    @Column (name = "created_At", nullable = false)
    private LocalDateTime createdAt;

    public ChatMessage (){

    }

    @PrePersist 
    public  void onCreate(){
        createdAt = LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public ChatSession getChatSession(){
        return chatSession;
    }
    public void setChatSession(ChatSession chatSession){
        this.chatSession = chatSession;
    }

    public String getSender(){
        return sender;
    }
    public void setSender(String sender){
        this.sender = sender;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    
}
