package com.basic.board.controller;

import com.basic.board.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    // get, post, put, patch, delete, options, head, trace, connect .. http method 종류
    // 글 등록 -> post method

    @PostMapping("/hello")
    public Map<String, String> post(@RequestBody @Valid PostCreate params){


        return Map.of();
    }

    //데이터 검증 이유
    // client 개발자가 실수로 값을 안 보내는 경우
    // client bug로 값 누락 가능
    // 외부에 누군가가 값을 임의로 조작해서 보내는 가능성
    // DB에 값 저장 시 의도치 않은 오류 발생
    // 서버 개발자의 편안함을 위해..



}
