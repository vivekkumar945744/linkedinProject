package com.vivekkumar.linkedinProject.userService.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException  extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
