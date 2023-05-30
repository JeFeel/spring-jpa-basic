package com.study.jpa.chap01_basic.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_product")
public class Product {
    @Id
    @Column(name="prod_id")
    private int prodId;
    @Column(name="prod_name", nullable = false, length = 30)
    private String prodName;


}
