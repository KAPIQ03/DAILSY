package com.dailsy.api.service;

import com.dailsy.api.dto.PostRequestDTO;
import com.dailsy.api.dto.PostResponseDTO;
import com.dailsy.api.models.Post;
import com.dailsy.api.models.User;
import com.dailsy.api.repositories.PostRepository;
import com.dailsy.api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDTO createPost(PostRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User currentUser = userRepository.findByEmail(currentPrincipalName).orElseThrow(() -> new RuntimeException("Logged in user not found in database"));

        Post newPost = new Post();
        newPost.setContent(request.getContent());
        newPost.setMood(request.getMood());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setUser(currentUser);

        Post savedPost = postRepository.save(newPost);

        return mapToPostResponseDTO(savedPost);
    }
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToPostResponseDTO)
                .collect(Collectors.toList());
    }

    private PostResponseDTO mapToPostResponseDTO(Post post) {
        String authorUsername = post.getUser() != null ? post.getUser().getUsername() : "Unknown";
        return new PostResponseDTO(
                post.getId(),
                post.getContent(),
                post.getMood(),
                post.getCreatedAt(),
                authorUsername
        );
    }
}
