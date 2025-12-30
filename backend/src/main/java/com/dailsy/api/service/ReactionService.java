package com.dailsy.api.service;

import com.dailsy.api.models.Post;
import com.dailsy.api.models.Reaction;
import com.dailsy.api.models.User;
import com.dailsy.api.repositories.PostRepository;
import com.dailsy.api.repositories.ReactionRepository;
import com.dailsy.api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ReactionService(ReactionRepository reactionRepository, PostRepository postRepository, UserRepository userRepository) {
        this.reactionRepository = reactionRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean toggleReaction(Long postId) {
        User user = getAuthenticatedUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        Optional<Reaction> existingReaction = reactionRepository.findByUserAndPost(user, post);

        if (existingReaction.isPresent()) {
            reactionRepository.delete(existingReaction.get());
            return false; // Reaction removed (unliked)
        } else {
            Reaction newReaction = new Reaction(user, post);
            reactionRepository.save(newReaction);
            return true; // Reaction added (liked)
        }
    }

    public long getReactionCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));
        return reactionRepository.countByPost(post);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        return userRepository.findByEmail(currentPrincipalEmail)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database."));
    }
}
