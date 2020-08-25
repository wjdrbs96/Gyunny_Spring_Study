<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>

    <link href="https://fonts.googleapis.com/css?family=Jua" rel="stylesheet">
    <style>
        body {
            font-family: 굴림체; }
        input.form-control {
            width: 200px;
        }
        .color-control {
            color: #58ccff;
        }
        h1 {
            margin-top: 50px;
        }
    </style>
</head>
<body>

<div class="container">

    <a href="http://localhost:8080/login"><h1 style="color: #0000FF;">로그인</h1></a>
    <hr />

    <form action="http://localhost:8080/login" method="post">
        <div class="form-group">
            <label>사용자 아이디</label>
            <input type="text" class="form-control" name="loginId" placeholder="아이디를 입력하세요."/>
        </div>
        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" class="form-control" name="password" placeholder="비밀번호를 입력하세요."/>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="autologin" /> 자동 로그인
            </label>
            <a class="color-control" href="http://localhost:8080/find/password">
                <button class="btn btn-link" type="button">비밀번호 찾기</button>
            </a>
        </div>
        <button type="submit" class="btn btn-primary">
            <i class="glyphicon glyphicon-ok"></i> 로그인
        </button>
        <a href="http://localhost:8080/register" class="btn btn-default">
            <i class="glyphicon glyphicon-user"></i> 회원가입
        </a>

    </form>

    <hr />

</div>
</body>
</html>
