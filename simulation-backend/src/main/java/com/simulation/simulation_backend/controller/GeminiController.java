package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.dto.GeminiChatRequest;
import com.simulation.simulation_backend.dto.GeminiChatResponse;
import com.simulation.simulation_backend.entity.ChatMessage;
import com.simulation.simulation_backend.entity.ChatSession;
import com.simulation.simulation_backend.entity.SimulationSession;
import com.simulation.simulation_backend.entity.SimulationSessionStatus;
import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.repository.ChatMessageRepositroy;
import com.simulation.simulation_backend.repository.ChatSessionRepository;
import com.simulation.simulation_backend.repository.SimulationSessionRepository;
import com.simulation.simulation_backend.repository.UserRepository;
import com.simulation.simulation_backend.service.ChatMessageService;
import com.simulation.simulation_backend.service.GeminiService;

import okhttp3.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping ("/api/ai")
public class GeminiController {

    private final GeminiService geminiService;
    private final UserRepository userRepository;
    private final SimulationSessionRepository simulationSessionRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageService chatMessageService;

    public GeminiController(GeminiService geminiService, UserRepository userRepository, 
                            SimulationSessionRepository simulationSessionRepository,
                            ChatSessionRepository chatSessionRepository,
                            ChatMessageService chatMessageService){
                                this.geminiService = geminiService;
                                this.userRepository = userRepository;
                                this.simulationSessionRepository = simulationSessionRepository;
                                this.chatSessionRepository = chatSessionRepository;
                                this.chatMessageService = chatMessageService;
                            }

    @PostMapping ("/chat")
    public ResponseEntity<GeminiChatResponse> caht(@RequestBody GeminiChatRequest request, Authentication authentication){
            if(request.message() == null || request.message().isBlank() || request.simulationSessionId() == null){
                    return ResponseEntity.badRequest().build();
            }

            Long userId;

            try{
                userId = Long.parseLong(authentication.getName());
            }catch(RuntimeException exception){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            User user = userRepository.findById(userId).orElse(null);

            if(user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            SimulationSession simulationSession = simulationSessionRepository.findByIdAndUserId(request.simulationSessionId(), userId).orElse(null);

            if(simulationSession == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if(simulationSession.getStatus() != SimulationSessionStatus.ACTIVE){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            ChatSession chatSession;
            
            if(request.chatSessionId() == null){
                chatSession = new ChatSession();
                chatSession.setUser(user);
                chatSession.setSimulationSession(simulationSession);
                chatSession = chatSessionRepository.save(chatSession);
            }else{
                chatSession = chatSessionRepository.findByIdAndUserId(request.chatSessionId(), userId).orElse(null);
                
                if(chatSession == null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                if(!chatSession.getSimulationSession().getId().equals(request.simulationSessionId())){
                    return ResponseEntity.badRequest().build();
                }
            }

            ChatMessage userMessage = new ChatMessage();
            userMessage.setChatSession(chatSession);
            userMessage.setSender("USER");
            userMessage.setMessage(request.message());

            chatMessageService.createChatMessage(userMessage);

            String reply;

            try{
                reply = geminiService.generateReply((request.message()));

            }catch(RuntimeException exception){
                throw exception;
            }

             ChatMessage assistanMessage = new ChatMessage();
             assistanMessage.setChatSession(chatSession);
             assistanMessage.setSender("ASSISTANT");
             assistanMessage.setMessage(reply);

             chatMessageService.createChatMessage(assistanMessage);

             GeminiChatResponse response = new GeminiChatResponse(reply, simulationSession.getId(), chatSession.getId());

             return ResponseEntity.ok(response);
    }

  
    
}
