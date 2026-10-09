package com.simulation.simulation_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "interactions")
public class Interactions {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn (name = "session_id", nullable = false)
    private SimulationSession simulationSession;

    @Column (name = "action", nullable = false)
    private String action;

    @Column (name = "object_name")
    private String objectName;

    @Column (name = "temprature")
    private Double temprature;

    @Column(columnDefinition = "TEXT", name = "data")
    private String data;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    public Interactions(){

    }

    @PrePersist 
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }

    public Long getId (){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public SimulationSession getSimulationSession(){
        return simulationSession;
    }
    public void setsimulationSession (SimulationSession simulationSession){
        this.simulationSession = simulationSession;
    }

    public String getAction (){
        return action;
    }
    public void setAction(String action){
        this.action = action;
    }

    public String getObjectName(){
        return objectName;
    }
    public void  setObjectName(String objectName){
        this.objectName = objectName;

    }

    public Double getTemprature (){
        return temprature;
    }
    public void setTemprature(double temprature){
        this.temprature = temprature;
    }

    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }

    public LocalDateTime getCreatedAt (){
        return createdAt;
    }





    
}
