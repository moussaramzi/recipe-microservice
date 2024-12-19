package com.example.commentservice.controller;

import com.example.commentservice.dto.CommentCreateRequest;
import com.example.commentservice.dto.CommentResponse;
import com.example.commentservice.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // GET /api/comments?recipeId=...
    @GetMapping
    public List<CommentResponse> getComments(@RequestParam String recipeId) {
        return commentService.getCommentsByRecipeId(recipeId);
    }

    @GetMapping("/{id}")
    public CommentResponse getCommentById(@PathVariable String id) {
        return commentService.getCommentById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
    }
}
