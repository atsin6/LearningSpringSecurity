package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//above 2 constructors are required by jackson to convert JSON to PostDto
public class PostDto {

    private Long id;

    private String title;

    private String description;
}
