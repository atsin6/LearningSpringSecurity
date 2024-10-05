package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto;

import lombok.Data;

@Data
public class LogoutDto {
    public Long userId;
    public String password;
}
