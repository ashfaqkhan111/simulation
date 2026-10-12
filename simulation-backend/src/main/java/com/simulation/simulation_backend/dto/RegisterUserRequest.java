package com.simulation.simulation_backend.dto;

public record RegisterUserRequest (
 String username,
 String email,
 String fullName,
 String password
){}
   
    

