package com.dailsy.api.dto;

public class PostRequestDTO {
    private String content;
    private Integer mood;

    public PostRequestDTO() {}

    public PostRequestDTO(String content, Integer mood) {
        this.content = content;
        this.mood = mood;
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
}
