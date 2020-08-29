package com.example.demo.service;

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

    // 생성자 의존성 주입
    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
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
        return DefaultRes.res(StatusCode.OK, ResponseMessage.FIND_ALL_POST);
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

        return DefaultRes.res(StatusCode.OK, ResponseMessage.FIND_ONE_POST);
    }

    /**
     * 게시글 등록
     *
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes insertPost(PostModel postModel) {
        try {
            final int postIdx = postMapper.insertPost(postModel);
            log.info(String.valueOf(postIdx));
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
    public DefaultRes updatePost(PostModel postModel, int postIdx) {
        Post post = postMapper.findByPostIdx(postIdx);
        if (post == null) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_POST);
        }
        try {
            postMapper.updatePost(postModel, postIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_POST);
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
    public DefaultRes deletePost(int postIdx) {
        Post post = postMapper.findByPostIdx(postIdx);

        if (post == null) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_POST);
        }
        try {
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
