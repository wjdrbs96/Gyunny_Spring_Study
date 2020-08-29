package com.example.demo.utils;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String ALREADY_USER = "존재하는 사용자입니다";
    public static final String EMPTY_TOKEN = "토큰이 존재하지 않습니다";
    public static final String INVALID_TOKEN = "토큰이 유효하지 않습니다";

    // 게시글
    public static final String FIND_ALL_POST = "게시글 전체 조회 성공";
    public static final String FIND_ONE_POST = "게시글 하나 조회 성공";
    public static final String CREATED_POST = "게시글 작성 성공";
    public static final String UPDATE_POST = "게시글 수정 성공";
    public static final String DELETE_POST = "게시글 삭제 성공";
    public static final String NOT_FOUND_POST = "게시글을 찾을 수 없습니다.";
    public static final String NOT_UPDATE_POST = "수정권한이 없습니다";

}
