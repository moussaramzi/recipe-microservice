package com.example.userservice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String profilePicture;
    private String bio;
    private Instant createdAt;
    private Instant updatedAt;
}
