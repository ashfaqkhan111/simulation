package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping 
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping ("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping ("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
      }

    @PutMapping 
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        User updateUser = userService.updateUser(user);
        return ResponseEntity.ok(updateUser);
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
