package com.simulation.simulation_backend.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "users")
public class User {
  
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column (nullable = false, unique = true)
private String username;

@Column (nullable = false, unique = true)
private String email;

@Column (name = "password_hash", nullable = false)
private String passwordHash;

@Column (name = "full_name")
private String fullName;

@Column (name = "created_at", nullable = false)
private LocalDateTime createdAt;

@Column (name = "updated_at", nullable = false)
private LocalDateTime updatedAt;

public User(){

}

@PrePersist 
protected void onCreate(){
    LocalDateTime now = LocalDateTime.now();
    createdAt = now;
    updatedAt = now;
}

@PreUpdate
protected void onUpdate(){
    updatedAt = LocalDateTime.now();
}

public Long getId(){
    return id;
}

public void setId(Long id){
    this.id = id;
}

public String getUsername(){
    return username;
}

public void setUsername (String username){
    this.username = username;
}

public String getEmail (){
    return email;
}

public void setEmail (String email){
    this.email = email;
}

public String getPasswordHash (){
    return passwordHash;
}

public void setPasswordHash(String passwordHash){
    this.passwordHash = passwordHash;
}

public String getFullName(){
    return fullName;
}

public void setFullName(String fullName){
    this.fullName = fullName;
}

public LocalDateTime getCreatedAt(){
    return createdAt;
}

public LocalDateTime getUpdatedAt(){
    return updatedAt;
}
    

}

