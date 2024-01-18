package com.socialmeda.service;

import com.socialmeda.dto.request.PostSaveRequestDto;
import com.socialmeda.entity.Post;
import com.socialmeda.manager.IUserManager;
import com.socialmeda.repository.IPostRepository;
import com.socialmeda.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostService extends ServiceManager<Post,Long> {

    private final IPostRepository repository;
    private final IUserManager userManager;

    public PostService(IPostRepository repository, IUserManager userManager) {
        super(repository);
        this.repository = repository;
        this.userManager = userManager;
    }

    public Post createPost(PostSaveRequestDto dto) {
        Long id = userManager.getUserIdFromToken(dto.getToken());
        Post post = Post.builder()
                .content(dto.getContent())
                .title(dto.getTitle())
                .photo(dto.getPhoto())
                .userId(id)
                .build();
        return save(post);
    }
}
