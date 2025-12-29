package com.dailsy.api.controller;

import com.dailsy.api.dto.UserResponseDTO;
import com.dailsy.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        UserResponseDTO user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe() {
        // TODO: Pobrac ID z kontekstu bezpieczenstwa (Spring Security)
        return ResponseEntity.ok(userService.getUserById(1L)); // Tymczasowo hardcoded ID 1
    }

    // TODO: Metody follow/unfollow i update zaimplementujemy pozniej w serwisie
}
