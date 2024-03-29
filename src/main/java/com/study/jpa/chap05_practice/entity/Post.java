package com.study.jpa.chap05_practice.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter @Getter
@ToString(exclude = {"hashTags"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long id;

    @Column(nullable = false)
    private String writer; // 작성자

    @Column(nullable = false)
    private String title;   // 제목
    private String content; // 게시글 내용

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; //작성시간

    @UpdateTimestamp
    private LocalDateTime updateDate; //수정시간

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @Builder.Default //빌더 기본값을 따로 주고 싶기 때문에 써야됨
    private List<HashTag> hashTags = new ArrayList<>();

    // 양방향 매핑에서 list쪽에 데이터를 추가하는 편의메서드 생성
    public void addHashTag(HashTag hashTag){
        hashTags.add(hashTag);
        if(this!= hashTag.getPost())   {
            hashTag.setPost(this);
        }
    }
}
