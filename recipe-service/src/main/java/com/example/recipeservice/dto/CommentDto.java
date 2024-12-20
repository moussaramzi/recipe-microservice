package com.example.recipeservice.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class CommentDto {
    private String id;
    private String userId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}
