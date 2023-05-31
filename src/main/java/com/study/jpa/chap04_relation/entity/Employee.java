package com.study.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;


@Setter
@Getter

// jpa 연관관계 매핑에서는 연관관계 데이터는 toString에서 제외해야 한다
// overflow 방지 차원에서
@ToString(exclude = {"department"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "emp_name", nullable = false)
    private String name;

    //다 대 1
    // EAGER: 항상 무조건 조인을 수행 (default)
    // LAZY: 필요한 경우에만 조인을 수행 (실무 사용)
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id") //fk 이름
    private Department department; // 단방향 연관관계


    // 1 대 다 관계에서는 다가 fk를 가지고 있어야됨


}
