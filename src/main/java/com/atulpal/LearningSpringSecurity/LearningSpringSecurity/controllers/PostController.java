package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.controllers;


import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.PostDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor//Generates a constructor that takes arguments for all final fields and
                        //non-initialized @NonNull fields, but not for fields without these qualifiers
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping()
    public PostDto createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @PutMapping("/{postId}")
    public PostDto updatePost(@RequestBody PostDto inputPost,
                              @PathVariable Long postId) {
        return postService.updatePost(inputPost, postId);
    }
}
