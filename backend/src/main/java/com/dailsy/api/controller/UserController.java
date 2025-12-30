package com.dailsy.api.controller;

import com.dailsy.api.dto.UserResponseDTO;
import com.dailsy.api.models.User;
import com.dailsy.api.repositories.UserRepository;
import com.dailsy.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        Long currentUserId = getCurrentUserId();
        return ResponseEntity.ok(userService.getAllUsers(currentUserId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        Long currentUserId = getCurrentUserId();
        UserResponseDTO user = userService.getUserById(id, currentUserId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe() {
        Long currentUserId = getCurrentUserId();
        if(currentUserId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        return ResponseEntity.ok(userService.getUserById(currentUserId,currentUserId)); // Tymczasowo hardcoded ID 1
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email).map(User::getId).orElse(null);
    }
}
