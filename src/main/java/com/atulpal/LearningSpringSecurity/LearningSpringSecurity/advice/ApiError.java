package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiError {

    private LocalDateTime timestamp;
    private String message;
    private HttpStatus statusCode;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String message, HttpStatus statusCode) {
        this();
        this.message = message;
        this.statusCode = statusCode;
    }
}
