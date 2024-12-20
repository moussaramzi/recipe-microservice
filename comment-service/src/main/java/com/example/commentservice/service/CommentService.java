package com.example.commentservice.service;

import com.example.commentservice.dto.*;
import com.example.commentservice.model.Comment;
import com.example.commentservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final WebClient webClient;

    @Value("${userservice.url:http://user-service:8081}")
    private String userServiceUrl;

    @Value("${recipeservice.url:http://recipe-service:8082}")
    private String recipeServiceUrl;

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

        // Fetch user info using WebClient
        if (comment.getUserId() != null && !comment.getUserId().isEmpty()) {
            try {
                UserDto user = webClient.get()
                        .uri(userServiceUrl + "/api/users/{id}", comment.getUserId())
                        .retrieve()
                        .bodyToMono(UserDto.class)
                        .block();
                response.setAuthor(user);
            } catch (Exception e) {
                // Handle error gracefully, leave author as null
            }
        }

        // Fetch recipe info using WebClient
        if (comment.getRecipeId() != null && !comment.getRecipeId().isEmpty()) {
            try {
                RecipeDto recipe = webClient.get()
                        .uri(recipeServiceUrl + "/api/recipes/{id}", comment.getRecipeId())
                        .retrieve()
                        .bodyToMono(RecipeDto.class)
                        .block();
                if (recipe != null) {
                    response.setRecipeTitle(recipe.getTitle());
                }
            } catch (Exception e) {
                // Handle error gracefully, leave recipeTitle as null
            }
        }

        return response;
    }
}
