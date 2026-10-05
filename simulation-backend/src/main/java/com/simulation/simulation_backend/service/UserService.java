package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service 
public class UserService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
    
}
