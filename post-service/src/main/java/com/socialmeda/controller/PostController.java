package com.socialmeda.controller;

import com.socialmeda.dto.request.PostSaveRequestDto;
import com.socialmeda.entity.Post;
import com.socialmeda.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(PostSaveRequestDto dto){
        return ResponseEntity.ok(postService.createPost(dto));
    }

    }
