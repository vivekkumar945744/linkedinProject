package com.vivekkumar.linkedinProject.postsService.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
