package com.example.demo.controller;

import com.example.demo.model.DefaultRes;
import com.example.demo.model.PostModel;
import com.example.demo.service.PostService;
import com.example.demo.utils.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PostController {

    private PostService postService;

    /**
     * PostService 생성자 의존성 주입
     *
     * @param PostService
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시글 전체 조회
     *
     * @param
     */
    @GetMapping("posts")
    public ResponseEntity findALl() {
        try {
            return new ResponseEntity(postService.findALl(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 게시글 하나 조회
     *
     * @param postIdx
     */
    @GetMapping("posts/{postIdx}")
    public ResponseEntity findOne(@PathVariable int postIdx) {
        try {
            return new ResponseEntity(postService.findOne(postIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 게시글 등록
     *
     * @param PostModel
     */
    @PostMapping("posts")
    public ResponseEntity createPost(@RequestHeader String token,
                                     @RequestBody PostModel postModel) {
        try {
            return new ResponseEntity(postService.insertPost(postModel, token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 게시글 수정
     *
     * @param PostModel
     */
    @Auth
    @PutMapping("posts/{postIdx}")
    public ResponseEntity postUpdate(@RequestHeader("token") String token,
                                     @PathVariable int postIdx,
                                     @RequestBody PostModel postModel) {
        try {
            return new ResponseEntity(postService.updatePost(postModel, postIdx, token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 게시글 삭제
     *
     * @param PostModel
     */
    @Auth
    @DeleteMapping("posts/{postIdx}")
    public ResponseEntity postDelete(@RequestHeader String token,
                                     @PathVariable int postIdx) {
        try {
            return new ResponseEntity(postService.deletePost(postIdx, token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
