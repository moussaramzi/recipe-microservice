package com.example.userservice.service;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.dto.UserLoginRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    // In a real application, you would inject a PasswordEncoder for secure password hashing:
    // @Autowired private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void loadData() {
        if (userRepository.count() <= 0) {
            User user1 = new User();
            user1.setUsername("johndoe");
            user1.setEmail("john@example.com");
            user1.setPassword("secret");
            user1.setBio("I love cooking and sharing recipes.");
            user1.setProfilePicture("https://example.com/john.jpg");
            user1.setCreatedAt(Instant.now());
            user1.setUpdatedAt(Instant.now());

            User user2 = new User();
            user2.setUsername("janedoe");
            user2.setEmail("jane@example.com");
            user2.setPassword("secret");
            user2.setBio("Pastry chef and dessert lover.");
            user2.setProfilePicture("https://example.com/jane.jpg");
            user2.setCreatedAt(Instant.now());
            user2.setUpdatedAt(Instant.now());

            userRepository.save(user1);
            userRepository.save(user2);
        }
    }

    public UserResponse registerUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // In real scenario: user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword(request.getPassword());
        user.setBio(request.getBio());
        user.setProfilePicture(request.getProfilePicture());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        User saved = userRepository.save(user);

        return mapToResponse(saved);
    }

    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        // In a real scenario, you'd compare password hashes.
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return mapToResponse(user);
    }

    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return mapToResponse(user);
    }

    public UserResponse updateUser(String id, UserCreateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if new email is different and already taken by another user
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already taken by another user");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword(request.getPassword());
        user.setBio(request.getBio());
        user.setProfilePicture(request.getProfilePicture());
        user.setUpdatedAt(Instant.now());

        User updated = userRepository.save(user);
        return mapToResponse(updated);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setProfilePicture(user.getProfilePicture());
        response.setBio(user.getBio());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
