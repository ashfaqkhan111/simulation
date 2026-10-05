package com.simulation.simulation_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "simulation_session")
public class SimulationSession {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
    
    @Column (name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column (name = "finished_at")
    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SimulationSessionStatus status;

    public SimulationSession (){

    }

    @PrePersist 
    protected void onCreate(){
        startedAt = LocalDateTime.now();

        if(status == null){
            throw new IllegalStateException("status must be set before persisting");
        }

    }

    public Long getId(){
        return id;
    }
    public User getUser (){
        return user;
    }
    public void  setUser(User user){
        this.user = user;
    }

    public LocalDateTime getStartedAt(){
        return startedAt;
    }
    
    public LocalDateTime getFinishedAt(){
        return finishedAt;
    }

    public void setFinishedAt (LocalDateTime finishedAt){
        this.finishedAt = finishedAt;
    }

    public SimulationSessionStatus getStatus(){
        return status;
    }
    public void setStatus(SimulationSessionStatus status){
        this.status = status;
    }
}
