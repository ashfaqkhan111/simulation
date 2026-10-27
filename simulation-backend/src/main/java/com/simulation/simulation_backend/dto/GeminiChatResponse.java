package com.simulation.simulation_backend.dto;

public record GeminiChatResponse(
    String reply,
    Long simulationSessionId,
    Long chatSessionId
) {
    
}
