package com.basic.board.service;

import com.basic.board.domain.Post;
import com.basic.board.repository.PostRepository;
import com.basic.board.request.PostCreate;
import com.basic.board.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }


    public List<PostResponse> getList() {
        return postRepository.findAll().stream()
                //.map(post -> new PostResponse(post))
                .map(PostResponse :: new)
                .collect(Collectors.toList());
    }
}
