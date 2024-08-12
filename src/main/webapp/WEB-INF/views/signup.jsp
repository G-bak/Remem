<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
<link href="/css/signup.css" rel="stylesheet">
<title>Insert title here</title>
</head>
<body class="signup-body">
	<img src="/image/background-melting-cheese-drip-yummy-melted-cheddar-cheese-melt-top-border_8580-1052-removebg-preview.png"
		 alt="Background Image">
	<h1>#오늘 일기</h1>
	<h2>회원가입</h2>
	<form action="/signin" method="get">
		<input type="text" id="username" placeholder="너의 이름이 뭐야?" required><br />
		<input type="text" id="id" placeholder="아이디를 입력해줘!" required><br />
		<input type="text" id="address" placeholder="주소 입력해줘!" required><br />
		<input type="date" id="signup-date" required><br />
		<button type="submit">
			회원가입<i class="fab fa-gratipay"></i>
		</button>
	</form>
</body>
</html>