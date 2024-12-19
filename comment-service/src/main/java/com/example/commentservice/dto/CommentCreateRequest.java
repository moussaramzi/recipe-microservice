package com.example.commentservice.dto;

import lombok.Data;


@Data
public class CommentCreateRequest {
    private String userId;
    private String recipeId;
    private String content;
}
