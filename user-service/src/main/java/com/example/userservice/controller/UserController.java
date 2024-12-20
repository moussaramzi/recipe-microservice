package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.dto.UserLoginRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Validated @RequestBody UserCreateRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping(params = "email")
    public UserResponse getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable String id, @Validated @RequestBody UserCreateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }
}
