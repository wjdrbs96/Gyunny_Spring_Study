package com.example.demo.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.dto.Post;
import com.example.demo.mapper.PostMapper;
import com.example.demo.model.DefaultRes;
import com.example.demo.model.PostModel;
import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Slf4j
public class PostService {

    private PostMapper postMapper;
    private JwtService jwtService;

    // 생성자 의존성 주입
    public PostService(PostMapper postMapper, JwtService jwtService) {
        this.postMapper = postMapper;
        this.jwtService = jwtService;
    }

    /**
     * 모든 게시글 조회
     *
     * @return DefaultRes
     */
    public DefaultRes findALl() {
        final List<Post> postList = postMapper.findAll();
        if (postList.isEmpty()) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_POST);
        }
        return DefaultRes.res(StatusCode.OK, ResponseMessage.FIND_ALL_POST, postList);
    }

    /**
     * 게시글 하나 조회
     *
     * @return DefaultRes
     */
    public DefaultRes findOne(int postIdx) {
        final Post post = postMapper.findByPostIdx(postIdx);
        if (post == null) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_POST);
        }

        return DefaultRes.res(StatusCode.OK, ResponseMessage.FIND_ONE_POST, post);
    }

    /**
     * 게시글 등록
     *
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes insertPost(PostModel postModel, String token) {
        try {
            JwtService.TOKEN decode = jwtService.decode(token);
            final int postIdx = postMapper.insertPost(postModel, decode.getMemberIdx());
            System.out.println(postIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_POST);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 게시글 수정
     *
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes updatePost(PostModel postModel, int postIdx, String token) {
        Post post = postMapper.findByPostIdx(postIdx);
        if (post == null) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_POST);
        }

        if (token == null) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.EMPTY_TOKEN);
        }

        try {
            // 토큰 해독
            JwtService.TOKEN decode = jwtService.decode(token);

            // 게시글 수정 권한이 없음
            if (decode.getMemberIdx() != post.getMemberIdx()) {
                return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOT_UPDATE_POST);
            }

            postMapper.updatePost(postModel, postIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_POST);
        } catch (JWTDecodeException jwt) {
            log.error(jwt.getMessage());
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.INVALID_TOKEN);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }


    /**
     * 게시글 삭제
     *
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes deletePost(int postIdx, String token) {
        Post post = postMapper.findByPostIdx(postIdx);

        if (post == null) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_POST);
        }
        try {
            JwtService.TOKEN decode = jwtService.decode(token);
            // 삭제 권한이 없을 때
            if (decode.getMemberIdx() != post.getMemberIdx()) {
                return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOT_DELETE_POST);
            }

            postMapper.deletePost(postIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_POST);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
