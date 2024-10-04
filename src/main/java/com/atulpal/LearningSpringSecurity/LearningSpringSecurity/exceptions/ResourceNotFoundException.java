package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
