package com.example.commentservice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CommentResponse {
    private String id;
    // Instead of showing just userId, we'll also show user info
    private String userId;
    private UserDto author;
    private String recipeId;
    private String recipeTitle;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}
