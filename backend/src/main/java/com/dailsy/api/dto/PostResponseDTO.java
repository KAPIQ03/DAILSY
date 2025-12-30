package com.dailsy.api.dto;

import java.time.LocalDateTime;

public class PostResponseDTO {
    private Long id;
    private String content;
    private Integer mood;
    private LocalDateTime createdAt;
    private String authorUsername;

    public PostResponseDTO() {}

    public PostResponseDTO(Long id, String content, Integer mood, LocalDateTime createdAt, String authorUsername) {
        this.id = id;
        this.content = content;
        this.mood = mood;
        this.createdAt = createdAt;
        this.authorUsername = authorUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
