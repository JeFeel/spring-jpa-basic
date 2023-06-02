package com.study.jpa.chap05_practice.api;


import com.study.jpa.chap05_practice.dto.*;
import com.study.jpa.chap05_practice.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.SpinnerUI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {


// 리소스 : 게시물 (Post)
/*
     게시물 목록 조회:  /posts       - GET
     게시물 개별 조회:  /posts/{id}  - GET
     게시물 등록:      /posts       - POST
     게시물 수정:      /posts/{id}  - PUT, PATCH
     게시물 삭제:      /posts/{id}  - DELETE
 */

    private final PostService postService;

    //게시물 목록조회
    @GetMapping
    public ResponseEntity<?> list(PageDTO pageDTO) {
        log.info("/api/v1/posts?page={}&size={}", pageDTO.getPage(), pageDTO.getSize());

        //게시물 목록 가져오기
        PostListResponseDTO dto = postService.getPosts(pageDTO); // 어떤 형식으로 데이터 받을지 요구


        return ResponseEntity.ok().body(dto);
    }

    //게시물 개별 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        log.info("/api/v1/posts/{} GET", id);

        try {
            PostDetailResponseDTO dto = postService.getDetail(id);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //게시물 등록
    @Parameters({
            @Parameter(name = "writer", description = "게시물의 작성자 이름을 쓰세요", example = "김베베", required = true)
            , @Parameter(name = "title", description = "게시물의 제목을 쓰세요", example = "에베베베베베ㅔ", required = true)
            , @Parameter(name = "content", description = "게시물의 내용을 쓰세요", example = "", required = true)
            , @Parameter(name = "hashTags", description = "게시물의 해시태그를 나열해주세요", example = "['하하', '호호']", required = true)

    })
    @PostMapping
    public ResponseEntity<?> create(
            @Validated @RequestBody PostCreateDTO dto
            , BindingResult result //검증 에러 정보 객체
    ) {

        log.info("/api/v1/post POST!! - payload : {}");

        if (dto == null) {
            return ResponseEntity.badRequest().body("등록 게시물 정보를 전달해주세요");
        }

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            PostDetailResponseDTO responseDTO = postService.insert(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("서버 터짐 : "+e.getMessage());
        }

    }

    private static ResponseEntity<List<FieldError>> getValidatedResult(BindingResult result) {
        if (result.hasErrors()) { //입력값 검증에 걸림
            List<FieldError> fieldErrors =
                    result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.info("invalid client data - {}", err.toString());
            });

            return ResponseEntity.badRequest().body(fieldErrors);
        }
        return null;
    }

    //게시물 수정
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> update(
            @Validated @RequestBody PostModifyDTO dto
            , BindingResult result
            , HttpServletRequest request
            ){
        //PostModifyDTO : 무엇을 수정할건지에 대한 정보 

        log.info("/api/v1/posts {}!! - dto: {}", request.getMethod(), dto);
        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            PostDetailResponseDTO responseDTO = postService.modify(dto);
            return ResponseEntity.ok(responseDTO);
            //header 등 추가로 넣을 사항 없으면 ok에 바로 넣어도 무방
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    //게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ){
        log.info("/api/v1/posts/{] DELETE!! ", id);

        try {
            postService.delete(id);
            return ResponseEntity.ok("DELETE SUCCESS");
        } catch (SQLIntegrityConstraintViolationException e){
            return ResponseEntity.internalServerError().body("해시태그 달린 게시글은 삭제가 불가능합니다");
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }


}
