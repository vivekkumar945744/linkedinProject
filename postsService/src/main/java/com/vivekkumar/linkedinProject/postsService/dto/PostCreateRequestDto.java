package com.vivekkumar.linkedinProject.postsService.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class PostCreateRequestDto {
    private String content;
}
