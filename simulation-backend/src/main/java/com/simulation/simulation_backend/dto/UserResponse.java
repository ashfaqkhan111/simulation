package com.simulation.simulation_backend.dto;

import java.time.LocalDateTime;

public record UserResponse (
    Long id,
    String username,
    String email,
    String fullName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){}
