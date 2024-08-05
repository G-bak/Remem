<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
<link href="/css/signin.css" rel="stylesheet">
<title>Insert title here</title>
</head>
<body class="signin-body">
    <img src="/image/background-melting-cheese-drip-yummy-melted-cheddar-cheese-melt-top-border_8580-1052-removebg-preview.png" alt="Background Image">
    <h1>#오늘 일기</h1>
    <h2>로그인</h2>
    <form action="/main" method="get">
        <input type="text" id="id" placeholder="아이디" required><br/>
        <input type="password" id="pw" placeholder="비밀번호" required><br/>
        <div class="checkbox">
            <input type="checkbox" id="remember-me">
            <label for="remember-me"> 로그인 정보 저장</label>
        </div>
        <button type="submit">로그인<i class="fab fa-gratipay"></i></button>
    </form>
</body>
</html>