package com.dailsy.api.dto;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private boolean isFollowing;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String username, String email, boolean isFollowing) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isFollowing = isFollowing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }
}
