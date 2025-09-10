package com.basic.board.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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

    @Builder //롬복 제공, Builder 패턴(자바 디자인 패턴)
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Builder의 장점
     * 1. 가독성이 좋다
     * 2. 값 생성에 대한 유연함
     * 3. 필요한 값만 받을 수 있다 -> 오버로딩 가능한 조건 찾아보기
     * 4. 객체의 불변성
     */
}
