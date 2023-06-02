package com.study.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString(exclude = {"employees"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    //사원 리스트

    //양방향 매핑에서는 상대방 엔터티 갱신에 관여할 수 없다
    // 단순히 읽기전용(조회)으로만 사용해야 함
    // mappedBy에는 상대방 엔터티에 join되는 필드명을 입력
    @OneToMany(mappedBy = "department")
    @Builder.Default
    //부서가 하나, 사원이 여러명
    private List<Employee> employees = new ArrayList<>(); //초기화까지 필수
    
}