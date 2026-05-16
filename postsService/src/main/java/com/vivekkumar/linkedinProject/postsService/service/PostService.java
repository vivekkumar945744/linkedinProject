package com.vivekkumar.linkedinProject.postsService.service;

import com.vivekkumar.linkedinProject.postsService.dto.PostCreateRequestDto;
import com.vivekkumar.linkedinProject.postsService.dto.PostDto;
import com.vivekkumar.linkedinProject.postsService.entity.PostEntity;
import com.vivekkumar.linkedinProject.postsService.exception.ResourceNotFoundException;
import com.vivekkumar.linkedinProject.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("Creating post for user with id: {}", userId);
        PostEntity postEntity = modelMapper.map(postCreateRequestDto, PostEntity.class);
        postEntity.setUserId(userId);
        postEntity = postRepository.save(postEntity);
        return modelMapper.map(postEntity, PostDto.class);
    }


    public PostDto getPost(Long postId) {
        log.info("Getting the post with Id: {}", postId);
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        return modelMapper.map(postEntity, PostDto.class);
    }

    public List<PostDto> getAllPostOfUser(Long userId) {
        log.info("Getting all the posts of a user with ID: {}", userId);
        List<PostEntity> postEntityList = postRepository.findByUserId(userId);
        return postEntityList.stream()
                .map((post) -> modelMapper.map(post, PostDto.class))
                .toList();
    }
}
