package com.example.commentservice.service;

import com.example.commentservice.dto.*;
import com.example.commentservice.model.Comment;
import com.example.commentservice.repository.CommentRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RestTemplate restTemplate;
    private final String userServiceUrl;
    private final String recipeServiceUrl;

    public CommentService(CommentRepository commentRepository, RestTemplate restTemplate, Environment env) {
        this.commentRepository = commentRepository;
        this.restTemplate = restTemplate;
        this.userServiceUrl = env.getProperty("userservice.url", "http://user-service:8081");
        this.recipeServiceUrl = env.getProperty("recipeservice.url", "http://recipe-service:8082");
    }

    public CommentResponse createComment(CommentCreateRequest request) {
        Comment comment = new Comment();
        comment.setUserId(request.getUserId());
        comment.setRecipeId(request.getRecipeId());
        comment.setContent(request.getContent());
        comment.setCreatedAt(Instant.now());
        comment.setUpdatedAt(Instant.now());

        Comment saved = commentRepository.save(comment);
        return mapToResponse(saved);
    }

    public List<CommentResponse> getCommentsByRecipeId(String recipeId) {
        List<Comment> comments = commentRepository.findByRecipeId(recipeId);
        return comments.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public CommentResponse getCommentById(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        return mapToResponse(comment);
    }


    public void deleteComment(String id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepository.deleteById(id);
    }

    private CommentResponse mapToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setUserId(comment.getUserId());
        response.setRecipeId(comment.getRecipeId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());

        // Fetch user info
        if (comment.getUserId() != null && !comment.getUserId().isEmpty()) {
            try {
                UserDto user = restTemplate.getForObject(userServiceUrl + "/api/users/" + comment.getUserId(), UserDto.class);
                response.setAuthor(user);
            } catch (Exception e) {
                // If cannot fetch user, just leave author as null or handle error gracefully
            }
        }

        // Fetch recipe info
        if (comment.getRecipeId() != null && !comment.getRecipeId().isEmpty()) {
            try {
                RecipeDto recipe = restTemplate.getForObject(recipeServiceUrl + "/api/recipes/" + comment.getRecipeId(), RecipeDto.class);
                if (recipe != null) {
                    response.setRecipeTitle(recipe.getTitle());
                }
            } catch (Exception e) {
                // If cannot fetch recipe, leave recipeTitle as null or handle error
            }
        }

        return response;
    }
}
