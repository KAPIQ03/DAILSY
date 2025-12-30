package com.dailsy.api.service;

import com.dailsy.api.dto.LoginRequestDTO;
import com.dailsy.api.dto.RegisterRequestDTO;
import com.dailsy.api.dto.UserProfileUpdateDTO;
import com.dailsy.api.dto.UserResponseDTO;
import com.dailsy.api.models.User;
import com.dailsy.api.repositories.FollowRepository;
import com.dailsy.api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FollowRepository followRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.followRepository = followRepository;
    }

    public UserResponseDTO registerUser(RegisterRequestDTO request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(newUser);
        return mapToDTO(savedUser, null);
    }

    public List<UserResponseDTO> getAllUsers(Long currentUserId) {
        User currentUser = currentUserId != null ? userRepository.findById(currentUserId).orElse(null) : null;
        return userRepository.findAll()
                .stream()
                .map(user -> mapToDTO(user, currentUser))
                .collect(Collectors.toList());
    }
    
    public UserResponseDTO getUserById(Long id, Long currentUserId) {
        User currentUser = currentUserId != null ? userRepository.findById(currentUserId).orElse(null) : null;
        return userRepository.findById(id)
                .map(user -> mapToDTO(user,currentUser))
                .orElse(null);
    }

    @Transactional
    public UserResponseDTO updateUserProfile(UserProfileUpdateDTO updateDTO) {
        User authenticatedUser = getAuthenticatedUser();

        if (updateDTO.getEmail() != null && !updateDTO.getEmail().equals(authenticatedUser.getEmail())) {
            if (userRepository.findByEmail(updateDTO.getEmail()).isPresent()) {
                throw new RuntimeException("Email is already taken.");
            }
            authenticatedUser.setEmail(updateDTO.getEmail());
        }

        if (updateDTO.getUsername() != null && !updateDTO.getUsername().isBlank()) {
            authenticatedUser.setUsername(updateDTO.getUsername());
        }

        User updatedUser = userRepository.save(authenticatedUser);
        return mapToDTO(updatedUser, updatedUser);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        return userRepository.findByEmail(currentPrincipalEmail)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database."));
    }


    private UserResponseDTO mapToDTO(User user, User currentUser) {
        boolean isFollowing = false;
        if(currentUser != null){
            if (!currentUser.getId().equals(user.getId())) {
                isFollowing = followRepository.findByFollowerAndFollowed(currentUser, user).isPresent();
            }
        }
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), isFollowing);
    }

    public String login(LoginRequestDTO request)  {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return jwtService.generateToken(user);
        }else{
            throw new RuntimeException("Invalid credentials");
        }
    }
}