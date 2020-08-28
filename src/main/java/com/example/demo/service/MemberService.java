package com.example.demo.service;

import com.example.demo.dto.Member;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.DefaultRes;
import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * UserMapper 생성자 의존성 주입
     *
     * @param userMapper, passwordEncoder
     */
    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 로그인
     *
     * @return DefaultRes
     */

    public DefaultRes signIn(final Member member) {
        try {
            Member m = memberMapper.checkById(member.getId());

            // 아이디가 틀렸을 때
            if (m == null) {
                return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
            }

            // parameter1 : rawPassword, parameter2 : encodePassword
            boolean check = passwordEncoder.matches(member.getPassword(), m.getPassword());

            // 로그인 성공
            if (check) {
                return new DefaultRes(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS);
            }

            return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new DefaultRes(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }


    /**
     * 회원가입
     *
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes signUp(final Member member) {
        try {
            // 아이디 중복 체크
            final Member m = memberMapper.checkById(member.getId());

            // 이미 유저가 존재할 때
            if (m != null) {
                return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER);
            }
            // 비밀번호 암호화
            String encodePassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encodePassword);
            memberMapper.insertMember(member);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
