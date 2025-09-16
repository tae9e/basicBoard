package com.basic.board.service;


import com.basic.board.domain.Post;
import com.basic.board.repository.PostRepository;
import com.basic.board.request.PostCreate;
import com.basic.board.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        //when
        postService.write(postCreate);

        //then
        Assertions.assertEquals(1l,postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다",post.getTitle());
        assertEquals("내용입니다",post.getContent());


    }

    @Test
    @DisplayName("글 하나 조회")
    void test2(){
        //given
        Post requestPost = Post.builder().title("제목 ㅎㅎ").content("haha").build();
        postRepository.save(requestPost);

        //when
        PostResponse response = postService.get(requestPost.getId());


        //then
        Assertions.assertNotNull(response);
        assertEquals("제목 ㅎㅎ",response.getTitle());
        assertEquals("haha",response.getContent());


    }

    @Test
    @DisplayName("글 조회")
    void test3(){
        //given

        postRepository.saveAll(List.of(
                Post.builder().title("제목 ㅎㅎ").content("haha").build(),
                Post.builder().title("제목11").content("ha11").build()
        ));


        //when
        List<PostResponse> posts = postService.getList();


        //then
        assertEquals(2,posts.size());


    }


}