package com.vivekkumar.linkedinProject.postsService.controller;

import com.vivekkumar.linkedinProject.postsService.dto.PostCreateRequestDto;
import com.vivekkumar.linkedinProject.postsService.dto.PostDto;
import com.vivekkumar.linkedinProject.postsService.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        PostDto postDto = postService.createPost(postCreateRequestDto, 1L);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPost(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> postDtos = postService.getAllPostOfUser(userId);
        return ResponseEntity.ok(postDtos);
    }

}
