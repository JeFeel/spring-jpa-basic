package com.study.jpa.chap01_basic.repository;


import com.study.jpa.chap01_basic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    //첫번째 제너릭은 entity, 두번째 제너릭은 pk의 타입
}
