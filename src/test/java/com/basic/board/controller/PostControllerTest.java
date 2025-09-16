package com.basic.board.controller;

import com.basic.board.domain.Post;
import com.basic.board.repository.PostRepository;
import com.basic.board.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test @DisplayName("/hello world 출력")
    void test() throws Exception{

        //given
        //ObjectMapper 자주 쓰임, 꼭 알아둘 것
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/posts") //application/json 형태로 보냄, key-value 형태
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수")
    void test2() throws Exception{
        //given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //expected
        mockMvc.perform(post("/posts") //application/json 형태로 보냄, key-value 형태
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력하세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다")
    void test3() throws Exception{
        //when
        mockMvc.perform(post("/posts") //application/json 형태로 보냄, key-value 형태
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"제목입니다\",\"content\":\"내용\"}"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
         assertEquals(1L,postRepository.count());

         Post post  = postRepository.findAll().get(0);
         assertEquals("제목입니다",post.getTitle());
         assertEquals("내용",post.getContent());


    }

    @Test
    @DisplayName("글 하나 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder().title("제목 ㅎㅎ12334567493939292").content("haha").build();
        postRepository.save(post);

        //클라이언트 요구사항 -> json 응답에서 title값 길이를 최대 10글자로 해주세요

        //expected
        mockMvc.perform(get("/posts/{postId}",post.getId()) //application/json 형태로 보냄, key-value 형태
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." +
                        "id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("제목 ㅎㅎ12334567493939292"))
                .andExpect(jsonPath("$.content").value("haha"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 조회")
    void test5() throws Exception {
        //given
        Post post = Post.builder().title("제목 ㅎㅎ12334567493939292").content("haha").build();
        postRepository.save(post);

        Post post2 = Post.builder().title("제목 11236").content("ha12424").build();
        postRepository.save(post2);

        //클라이언트 요구사항 -> json 응답에서 title값 길이를 최대 10글자로 해주세요

        //expected
        mockMvc.perform(get("/posts") //application/json 형태로 보냄, key-value 형태
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                //json의 길이 구하기
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value("제목 ㅎㅎ12334567493939292"))
                .andExpect(jsonPath("$[0].content").value("haha"))

                .andDo(print());

    }


}