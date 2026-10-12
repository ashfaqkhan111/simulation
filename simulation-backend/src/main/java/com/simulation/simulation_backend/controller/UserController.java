package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.service.UserService;
import com.simulation.simulation_backend.dto.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController 
@RequestMapping ("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping 
    public ResponseEntity<UserResponse> createUser(@RequestBody RegisterUserRequest request){
        UserResponse createdUser = userService.createUser((request));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @GetMapping 
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping ("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<UserResponse> updateProfile(@PathVariable  Long id, @RequestBody UpdateProfileRequest request){
        return ResponseEntity.ok(userService.updateProfile(id, request));
    }

    @PutMapping ("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request){
        userService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
}
