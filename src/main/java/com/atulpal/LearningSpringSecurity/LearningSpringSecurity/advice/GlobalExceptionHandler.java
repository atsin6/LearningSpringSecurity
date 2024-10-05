package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.advice;


import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getLocalizedMessage())
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getLocalizedMessage())
                .statusCode(HttpStatus.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    //This will not handle the JwtException because this exception doesn't occur in dispatcher servlet context holder
    //It will occur in filter context
    //GlobalExceptionHandler only applicable for the exceptions that will occur in dispatcher servlet context holder
    //We have to use HandleExceptionResolver in our filter so that it can pass the exception from filter context to dispatcher servlet context
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getLocalizedMessage())
                .statusCode(HttpStatus.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getLocalizedMessage())
                .statusCode(HttpStatus.CONFLICT)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}
