package com.dailsy.api.service;

import com.dailsy.api.dto.FollowResponseDTO;
import com.dailsy.api.models.Follow;
import com.dailsy.api.models.User;
import com.dailsy.api.repositories.FollowRepository;
import com.dailsy.api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FollowResponseDTO followUser(Long followedId){
        User follower = getAuthenticatedUser();
        User followed = userRepository.findById(followedId).orElseThrow(()-> new RuntimeException("User to follow not found"));

        if(follower.getId().equals(followed.getId())){
            throw new RuntimeException("Cannot follow yourself");
        }

        if(followRepository.findByFollowerAndFollowed(follower, followed).isPresent()){
            throw new RuntimeException("Already following this user");
        }

        Follow follow = new Follow(follower, followed);
        Follow savedFollow = followRepository.save(follow);

        return mapToFollowResponseDTO(savedFollow);
    }

    public List<FollowResponseDTO> getFollowers() {
        User user = getAuthenticatedUser();
        return followRepository.findByFollowed(user)
                .stream()
                .map(this::mapToFollowResponseDTO)
                .collect(Collectors.toList());
    }

    public List<FollowResponseDTO> getFollowing() {
        User user = getAuthenticatedUser();
        return followRepository.findByFollower(user)
                .stream()
                .map(this::mapToFollowResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void unfollowUser(Long followedUserId){
        User follower = getAuthenticatedUser();
        User followed = userRepository.findById(followedUserId).orElseThrow(()-> new RuntimeException("User to unfollow not found"));

        if(follower.getId().equals(followed.getId())){
            throw new RuntimeException("Cannot unfollow yourself");
        }

        Follow follow = followRepository.findByFollowerAndFollowed(follower, followed)
                .orElseThrow(() -> new RuntimeException("Not following this user"));

        followRepository.delete(follow);
    }
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        return userRepository.findByEmail(currentPrincipalEmail).orElseThrow(()-> new RuntimeException("Authenticated user not found"));
    }

    private FollowResponseDTO mapToFollowResponseDTO(Follow follow) {
        return new FollowResponseDTO(
                follow.getId(),
                follow.getFollower().getUsername(),
                follow.getFollowed().getUsername(),
                follow.getFollowedAt()
        );
    }

}
