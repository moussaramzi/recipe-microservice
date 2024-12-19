package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;
    private String email;
    private String password;
    private String bio;
    private String profilePicture;
}
