package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.PostDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.PostEntity;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.User;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.exceptions.ResourceNotFoundException;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.repositories.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepo
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public PostDto getPostById(Long postId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("user {}", user);

        PostEntity postEntity = postRepo
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: "+ postId));
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostDto createPost(PostDto inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        postRepo.save(postEntity);
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto inputPost, Long postId) {
        PostEntity olderPost = postRepo
                .findById(postId)
                .orElseThrow(() ->new ResourceNotFoundException("Post not found with ID: "+ postId));
        inputPost.setId(olderPost.getId());

        //all the changes will move from inputPost to olderPost
        modelMapper.map(inputPost, olderPost);

        PostEntity savedPostEntity = postRepo.save(olderPost);
        return modelMapper.map(savedPostEntity, PostDto.class);
    }
}
