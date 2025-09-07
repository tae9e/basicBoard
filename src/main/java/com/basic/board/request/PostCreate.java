package com.basic.board.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter @Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력하세요")
    private String title;

    @NotBlank(message = "컨텐츠를 입력하세요")
    private String content;
}
