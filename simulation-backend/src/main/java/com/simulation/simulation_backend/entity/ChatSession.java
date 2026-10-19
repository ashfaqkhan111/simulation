package com.simulation.simulation_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity 
@Table (name = "chat_sessions")
public class ChatSession {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "simulation_session_id", nullable = false)
    private SimulationSession simulationSession;

    @Column (name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ChatSession(){

    }

    @PrePersist 
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }

    public Long getId(){
        return id;

    }
    public void setId(Long id){
        this.id = id;
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    public SimulationSession getSimulationSession(){
         return simulationSession;
    }
    public void setSimulationSession(SimulationSession simulationSession){
        this.simulationSession = simulationSession;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    
}
