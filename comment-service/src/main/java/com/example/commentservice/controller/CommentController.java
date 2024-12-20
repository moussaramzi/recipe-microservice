package com.example.commentservice.controller;

import com.example.commentservice.dto.CommentCreateRequest;
import com.example.commentservice.dto.CommentResponse;
import com.example.commentservice.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@Validated @RequestBody CommentCreateRequest request) {
        return commentService.createComment(request);
    }

    @GetMapping("/{id}")
    public CommentResponse getCommentById(@PathVariable String id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("/by-recipe-ids")
    public Map<String, List<CommentResponse>> getCommentsByRecipeIds(@RequestBody List<String> recipeIds) {
        return commentService.getCommentsByRecipeIds(recipeIds);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
    }
}
