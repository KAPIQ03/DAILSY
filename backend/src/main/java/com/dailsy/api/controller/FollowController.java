package com.dailsy.api.controller;

import com.dailsy.api.dto.FollowResponseDTO;
import com.dailsy.api.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{followedUserId}")
    public ResponseEntity<FollowResponseDTO> followUser(@PathVariable Long followedUserId) {
        try{
            FollowResponseDTO follow = followService.followUser(followedUserId);
            return new ResponseEntity<>(follow, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @DeleteMapping("/{followedUserId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followedUserId) {
        try{
            followService.unfollowUser(followedUserId);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/followers")
    public ResponseEntity<List<FollowResponseDTO>> getFollowers() {
        List<FollowResponseDTO> followers = followService.getFollowers();
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following")
    public ResponseEntity<List<FollowResponseDTO>> getFollowing() {
        List<FollowResponseDTO> following = followService.getFollowing();
        return ResponseEntity.ok(following);
    }

}
