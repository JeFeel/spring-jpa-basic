package com.study.jpa.chap01_basic.repository;


import com.study.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.study.jpa.chap01_basic.entity.Product.*;
import static com.study.jpa.chap01_basic.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @BeforeEach //테스트 돌리기 전에 실행
    void insertDummyData(){
        //given
        Product p1 = builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(1000000)
                .build();
        Product p2 = builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = builder()
                .name("구두")
                .category(FASHION)
                .price(1000000)
                .build();
        Product p4 = builder()
                .name("쓰레기")
                .category(FOOD)
                .build();
        //when

        Product save1 = productRepository.save(p1);
        Product save2 =productRepository.save(p2);
        Product save3 = productRepository.save(p3);
        Product save4 =productRepository.save(p4);

    }

    @Test
    @DisplayName("상품 4개를 데이터베이스에 저장해야 한다")
    void testSave() {
        Product product = builder()
                .name("정장")
                .price(1200000)
                .category(FASHION)
                .build();
        Product save = productRepository.save(product);
        assertNotNull(save);
    }

    @Test
    @DisplayName("id가 2번인 데이터를 삭제해야 한다")
    void testRemove() {
        //given
        long id = 2L;
        //when
        productRepository.deleteById(id);
        //then
    }

    @Test
    @DisplayName("상품 전체 조회를 하면 상품의 개수가 4개여야 한다")
    void testFindAll() {
        //given

        //when
        List<Product> products = productRepository.findAll();
        //then
        products.forEach(System.out::println);

        assertEquals(4, products.size());
    }
    
    @Test
    @DisplayName("3번 상품을 조회하면 상품명이 '구두'여야 한다")
    void testFindOne() {
        //given
        Long id = 3L;
        //when
        Optional<Product> product= productRepository.findById(id);
        //Optional은 null check의 편의성을 위한 기능
        //then
        product.ifPresent(p -> {
            assertEquals("구두", p.getName());
        });


        Product foundProduct = product.get();
        assertNotNull(foundProduct);

        System.out.println(foundProduct);
    }

    @Test
    @DisplayName("2번 상품의 이름과 가격을 변경해야 한다")
    void testModify() {
        //given
        long id = 2L;
        String newName = "짜장면";
        int newPrice = 6000;
        //when
        //jpa에서 update는 따로 메서드를 지원하지 않으나
        // 조회 후 setter로 변경하면 update문이 적용된다
        // 변경 후 save를 다시 호출하라

        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p->{
            p.setName(newName);
            p.setPrice(newPrice);

            productRepository.save(p);
        });
        //then
        assertTrue(product.isPresent()); //존재하냐 안 하냐 판별
        Product p = product.get();
        assertEquals("짜장면", p.getName());
    }
}