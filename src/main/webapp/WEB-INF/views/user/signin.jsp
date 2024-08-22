<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
<link href="/css/signin.css" rel="stylesheet">
<title>#Remem</title>
</head>
<body class="signin-body">
    <h1>#오늘 일기</h1>
    <h2>로그인</h2>
    <form action="/user/signin" method="post">
        <input type="text" id="id" name="userId" placeholder="아이디" required><br/>
        <input type="password" id="pw" name="userPassword" placeholder="비밀번호" required><br/>
        
        <button type="submit">로그인<i class="fab fa-gratipay"></i></button>
    </form>
    
    <script>
	   // JSP 내에서 사용자가 제출한 폼에 대해 발생한 오류 메시지를 알림으로 보여줌
	    <c:if test="${not empty errorMessage}">
	        alert("${errorMessage}");
	    </c:if>
	</script>
</body>
</html>