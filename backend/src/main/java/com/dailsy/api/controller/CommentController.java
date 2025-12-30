package com.dailsy.api.controller;

import com.dailsy.api.dto.CommentRequestDTO;
import com.dailsy.api.dto.CommentResponseDTO;
import com.dailsy.api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long postId, @RequestBody CommentRequestDTO request) {
        try{
            CommentResponseDTO newComment = commentService.addCommentToPost(postId, request);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long postId) {
        try{
            List<CommentResponseDTO> comments = commentService.getCommentsForPost(postId);
            return ResponseEntity.ok(comments);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus. NOT_FOUND).body(List.of());
        }
    }
}
