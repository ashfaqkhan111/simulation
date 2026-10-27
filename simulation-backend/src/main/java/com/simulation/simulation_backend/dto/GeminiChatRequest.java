package com.simulation.simulation_backend.dto;

public record GeminiChatRequest(
    String message,
    Long simulationSessionId,
    Long chatSessionId
) {
} 
