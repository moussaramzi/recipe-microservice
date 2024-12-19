// src/main/java/com/example/recipeservice/dto/UserDto.java
package com.example.recipeservice.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String profilePicture;
    private String bio;
    private Instant createdAt;
    private Instant updatedAt;
}
