package com.dailsy.api.dto;

import java.time.LocalDateTime;

public class FollowResponseDTO {
    private Long id;
    private String followerUsername;
    private String followedUsername;
    private LocalDateTime followedAt;

    public FollowResponseDTO() {}

    public FollowResponseDTO(Long id, String followerUsername, String followedUsername, LocalDateTime followedAt) {
        this.id = id;
        this.followerUsername = followerUsername;
        this.followedUsername = followedUsername;
        this.followedAt = followedAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFollowerUsername() {
        return followerUsername;
    }
    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }
    public String getFollowedUsername() {
        return followedUsername;
    }
    public void setFollowedUsername(String followedUsername) {
        this.followedUsername = followedUsername;
    }
    public LocalDateTime getFollowedAt() {
        return followedAt;
    }
    public void setFollowedAt(LocalDateTime followedAt) {
        this.followedAt = followedAt;
    }
}
