package com.simulation.simulation_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 

@Table (name = "refresh_tokens",indexes = {@Index(name = "idx_refresh_roken_user_id", columnList = "user_id"),
    @Index(name = "idx_refresh_token_expires_at", columnList = "expires_at")
})
public class RefreshToken {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column (name = "token_hash", nullable = false, unique = true, length = 64)
    private String tokenHash;

    @Column (name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column (name = "revoked", nullable = false)
    private boolean revoked = false;

    @Column (name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    public RefreshToken(){

    }

    @PrePersist 
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getTokenHash(){
        return tokenHash;
    }

    public void setTokenHash(String tokenHsash){
        this.tokenHash = tokenHsash;
    }

    public LocalDateTime getExpiresAt(){
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expireAt){
        this.expiresAt = expireAt;
    }

    public boolean isRevoked(){
        return revoked;
    }
    
    public void setRevoked(boolean revoked){
        this.revoked =revoked;
    }

    public LocalDateTime getCrDateTime(){
        return createdAt;
    }
    
}
