package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SessionDto {
    private Long id;

    private Long userId;

    private String sessionToken;

    private boolean isActive = true;
}
