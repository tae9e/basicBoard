package com.basic.board.controller;

import com.basic.board.request.PostCreate;
import com.basic.board.response.PostResponse;
import com.basic.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    // get, post, put, patch, delete, options, head, trace, connect .. http method 종류
    // 글 등록 -> post method
    private final PostService postService;

    @PostMapping("/posts")
    //Post => 200, 201
    public Map<String, String> post(@RequestBody @Valid PostCreate request){
        // 저장한 데이터 Entity -> response로 응답
        // 저장한 데이터의 primary-id -> response로 응답
        //      Client에서는 수신한 id를 post 글 조회 API를 통해 데이터를 수신받음
        // 응답 필요없음
        //   -> Client에서 모든 POST(글) 데이터 context를 잘 관리함
        // Bad Case: 서버에서 이렇게 할 것이다 fix
        //           -> 서버에서는 유연한 대응이 좋다

        postService.write(request);

        return Map.of();
    }

    //데이터 검증 이유
    // client 개발자가 실수로 값을 안 보내는 경우
    // client bug로 값 누락 가능
    // 외부에 누군가가 값을 임의로 조작해서 보내는 가능성
    // DB에 값 저장 시 의도치 않은 오류 발생
    // 서버 개발자의 편안함을 위해..

    /**
     * posts -> 글 전체 조회(검색 + 페이징)
     * /posts/{postId} -> 글 한 개만 조회
     * /posts          -> 여러 개의 글 조회
     */

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name="postId") Long postId){
        // Request 클래스(PostCreate)
        // Response 클래스(PostResponse)
        return postService.get(postId);
        //응답 클래스 분리!!(서비스 정책에 맞는 응답 전용 클래스)

    }

    @GetMapping("/posts")
    public List<PostResponse> getList(){
        return postService.getList();
    }




}
