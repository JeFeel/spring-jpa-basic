package com.study.jpa.chap05_practice.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.jpa.chap05_practice.entity.HashTag;
import com.study.jpa.chap05_practice.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PostDetailResponseDTO {

    //entity랑 똑같을지라도 따로 파서 활용하는게 좋음

    private String title;
    private String content;
    private String author;
    private List<String> hashTags;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate;

    // 엔터티를 DTO로 변환하는 생성자
    public PostDetailResponseDTO(Post post){
       this.author = post.getWriter();
       this.title = post.getTitle();
       this.content= post.getContent();
       this.regDate = post.getCreateDate();
       this.hashTags = post.getHashTags()
               .stream()
               .map(HashTag::getTagName)
               .collect(Collectors.toList());
    }
}
