package com.example.commentservice;

import com.example.commentservice.dto.CommentCreateRequest;
import com.example.commentservice.dto.CommentResponse;
import com.example.commentservice.model.Comment;
import com.example.commentservice.repository.CommentRepository;
import com.example.commentservice.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentCreateRequest createRequest;
    private Comment sampleComment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createRequest = new CommentCreateRequest();
        createRequest.setUserId("123");
        createRequest.setRecipeId("456");
        createRequest.setContent("This is a great recipe!");

        sampleComment = new Comment();
        sampleComment.setId("1");
        sampleComment.setUserId("123");
        sampleComment.setRecipeId("456");
        sampleComment.setContent("This is a great recipe!");
        sampleComment.setCreatedAt(Instant.now());
        sampleComment.setUpdatedAt(Instant.now());
    }

    @Test
    void testCreateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(sampleComment);

        CommentResponse response = commentService.createComment(createRequest);

        assertNotNull(response);
        assertEquals("123", response.getUserId());
        assertEquals("456", response.getRecipeId());
        assertEquals("This is a great recipe!", response.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testGetCommentById() {
        when(commentRepository.findById("1")).thenReturn(Optional.of(sampleComment));

        CommentResponse response = commentService.getCommentById("1");

        assertNotNull(response);
        assertEquals("123", response.getUserId());
        assertEquals("456", response.getRecipeId());
        assertEquals("This is a great recipe!", response.getContent());
        verify(commentRepository, times(1)).findById("1");
    }

    @Test
    void testGetCommentById_NotFound() {
        when(commentRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            commentService.getCommentById("1");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Comment not found", exception.getReason());
        verify(commentRepository, times(1)).findById("1");
    }
}
