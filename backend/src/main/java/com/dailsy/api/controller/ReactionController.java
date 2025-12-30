package com.dailsy.api.controller;

import com.dailsy.api.service.ReactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts/{postId}/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Boolean>> toggleReaction(@PathVariable Long postId) {
        try {
            boolean liked = reactionService.toggleReaction(postId);
            return ResponseEntity.ok(Map.of("liked", liked));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getReactionCount(@PathVariable Long postId) {
        try {
            long count = reactionService.getReactionCount(postId);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
