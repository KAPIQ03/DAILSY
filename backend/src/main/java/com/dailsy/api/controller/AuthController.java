package com.dailsy.api.controller;

import com.dailsy.api.dto.RegisterRequestDTO;
import com.dailsy.api.dto.UserResponseDTO;
import com.dailsy.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        UserResponseDTO newUser = userService.registerUser(request);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        // TODO: Implementacja logowania (JWT)
        return ResponseEntity.ok("Login endpoint - work in progress");
    }
}
