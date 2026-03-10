package com.dailsy.api.service;

import com.dailsy.api.dto.PostRequestDTO;
import com.dailsy.api.dto.PostResponseDTO;
import com.dailsy.api.models.Post;
import com.dailsy.api.models.User;
import com.dailsy.api.models.Follow;
import com.dailsy.api.repositories.FollowRepository;
import com.dailsy.api.repositories.PostRepository;
import com.dailsy.api.repositories.ReactionRepository;
import com.dailsy.api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final FollowRepository followRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, ReactionRepository reactionRepository, FollowRepository followRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.reactionRepository = reactionRepository;
        this.followRepository = followRepository;
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

        return mapToPostResponseDTO(savedPost, currentUser);
    }
    public List<PostResponseDTO> getAllPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
             String currentPrincipalName = authentication.getName();
             currentUser = userRepository.findByEmail(currentPrincipalName).orElse(null);
        }
        
        User finalCurrentUser = currentUser;
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> mapToPostResponseDTO(post, finalCurrentUser))
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getFeedPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = userRepository.findByEmail(currentPrincipalName).orElseThrow(() -> new RuntimeException("User not found"));

        List<Follow> following = followRepository.findByFollower(currentUser);
        List<Long> followedUserIds = following.stream()
                .map(follow -> follow.getFollowed().getId())
                .collect(Collectors.toList());
        
        // Add current user to feed as well (optional, but standard practice)
        followedUserIds.add(currentUser.getId());

        if (followedUserIds.isEmpty()) {
            return Collections.emptyList();
        }

        return postRepository.findByUserIdInOrderByCreatedAtDesc(followedUserIds)
                .stream()
                .map(post -> mapToPostResponseDTO(post, currentUser))
                .collect(Collectors.toList());
    }

    private PostResponseDTO mapToPostResponseDTO(Post post, User currentUser) {
        String authorUsername = post.getUser() != null ? post.getUser().getUsername() : "Unknown";
        Long authorId = post.getUser() != null ? post.getUser().getId() : null;
        boolean hasReacted = false;
        if(currentUser != null){
            hasReacted = reactionRepository.findByUserAndPost(currentUser, post).isPresent();
        }

        return new PostResponseDTO(
                post.getId(),
                post.getContent(),
                post.getMood(),
                post.getCreatedAt(),
                authorUsername,
                authorId,
                hasReacted
        );
    }
}
