package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services;


import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createPost(PostDto inputPost);

    PostDto getPostById(Long postId);

    PostDto updatePost(PostDto inputPost, Long postId);
}
