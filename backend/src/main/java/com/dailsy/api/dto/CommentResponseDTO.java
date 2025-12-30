package com.dailsy.api.dto;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String authorUsername;
    private Long postId;

    public CommentResponseDTO() {}

    public CommentResponseDTO(Long id, String content, LocalDateTime createdAt, String authorUsername, Long postId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.authorUsername = authorUsername;
        this.postId = postId;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

}