package com.dailsy.api.service;

import com.dailsy.api.dto.LoginRequestDTO;
import com.dailsy.api.dto.RegisterRequestDTO;
import com.dailsy.api.dto.UserResponseDTO;
import com.dailsy.api.models.User;
import com.dailsy.api.repositories.FollowRepository;
import com.dailsy.api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private UserResponseDTO mapToDTO(User user, User currentUser) {
        boolean isFollowing = false;
        if(currentUser != null){
            isFollowing = followRepository.findByFollowerAndFollowed(currentUser, user).isPresent();
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
