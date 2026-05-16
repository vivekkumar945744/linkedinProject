package com.vivekkumar.linkedinProject.postsService.exception;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
