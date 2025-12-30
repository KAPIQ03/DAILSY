package com.dailsy.api.service;

import com.dailsy.api.dto.CommentRequestDTO;
import com.dailsy.api.dto.CommentResponseDTO;
import com.dailsy.api.models.Comment;
import com.dailsy.api.models.Post;
import com.dailsy.api.models.User;
import com.dailsy.api.repositories.CommentRepository;
import com.dailsy.api.repositories.PostRepository;
import com.dailsy.api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CommentResponseDTO addCommentToPost(Long postId, CommentRequestDTO request) {
        User author = getAuthenticatedUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        Comment newComment = new Comment();
        newComment.setContent(request.getContent());
        newComment.setAuthor(author);
        newComment.setPost(post);
        newComment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(newComment);

        return mapToCommentResponseDTO(savedComment);
    }

    public List<CommentResponseDTO> getCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));
        
        return commentRepository.findByPostOrderByCreatedAtAsc(post)
                .stream()
                .map(this::mapToCommentResponseDTO)
                .collect(Collectors.toList());
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        return userRepository.findByEmail(currentPrincipalEmail)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database."));
    }

    private CommentResponseDTO mapToCommentResponseDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getAuthor().getUsername(),
                comment.getPost().getId()
        );
    }
}
