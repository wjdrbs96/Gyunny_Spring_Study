package com.example.demo.service;

import com.example.demo.dto.Member;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.DefaultRes;
import com.example.demo.model.LoginModel;
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
    private final JwtService jwtService;

    /**
     * UserMapper, PasswordEncoder, JwtService 생성자 의존성 주입
     *
     * @param userMapper, passwordEncoder, JwtService
     */
    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * 로그인
     *
     * @return DefaultRes
     */

    public DefaultRes<JwtService.TokenRes> signIn(final LoginModel loginModel) {
        try {
            Member member = memberMapper.checkById(loginModel.getId());

            // 아이디가 틀렸을 때
            if (member == null) {
                return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
            }

            // parameter1 : rawPassword, parameter2 : encodePassword
            boolean check = passwordEncoder.matches(loginModel.getPassword(), member.getPassword());

            // 로그인 성공
            if (check) {
                // 토큰 생성
                JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(member.getMemberIdx()));
                return new DefaultRes(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
            }

            // 비밀번호가 틀렸을 때
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
    public DefaultRes<JwtService.TokenRes> signUp(final LoginModel loginModel) {
        try {
            // 아이디 중복 체크
            final Member member = memberMapper.checkById(loginModel.getId());

            if (member == null) {
                // 비밀번호 암호화
                String encodePassword = passwordEncoder.encode(loginModel.getPassword());
                Member member1 = new Member(loginModel.getId(), encodePassword);

                memberMapper.insertMember(member1);

                // 토큰 생성
                final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(member1.getMemberIdx()));
                return DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER, tokenDto);
            }

            // 이미 유저가 존재할 때
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER);

        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
