package com.basic.board.service;

import com.basic.board.domain.Post;
import com.basic.board.repository.PostRepository;
import com.basic.board.request.PostCreate;
import com.basic.board.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;


    public void write(PostCreate postCreate){
        Post post = Post.builder()
                        .title(postCreate.getTitle())
                        .content(postCreate.getContent()).build();
        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        /**
         * Optional로 반환값이 넘어올 경우 바로 처리해야 함
         * 없을 때의 조치를 즉시 처리
         */
        //Optional<Post> post = postRepository.findById(id);
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;


    }


}
