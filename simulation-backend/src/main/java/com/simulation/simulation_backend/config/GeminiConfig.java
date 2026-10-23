package com.simulation.simulation_backend.config;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class GeminiConfig {

    @Bean 
    public Client geminiClient(){
        String apiKey = System.getenv("GOOGLE_API_KEY");

        if(apiKey == null || apiKey.isBlank()){
            throw new IllegalStateException("GOOGLE_API_KEY enviroment variable is not configured");
        }

        return Client.builder().apiKey(apiKey).build();
    }
    
}
