package com.vivekkumar.linkedinProject.postsService.service;

import com.vivekkumar.linkedinProject.postsService.entity.PostEntity;
import com.vivekkumar.linkedinProject.postsService.entity.PostLike;
import com.vivekkumar.linkedinProject.postsService.exception.BadRequestException;
import com.vivekkumar.linkedinProject.postsService.exception.ResourceNotFoundException;
import com.vivekkumar.linkedinProject.postsService.repository.PostLikeRepository;
import com.vivekkumar.linkedinProject.postsService.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("User with ID: {} liking the post with ID: {}", userId, postId);

        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: "+ postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if (hasAlreadyLiked)
            throw new BadRequestException("You cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);

        postLikeRepository.save(postLike);

        // TODO: send notification to the owner of the post
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;
        log.info("User with ID: {} unliking the post with ID: {}", userId, postId);

        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: "+ postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if (!hasAlreadyLiked)
            throw new BadRequestException("You cannot unlike the post that you have not like");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
