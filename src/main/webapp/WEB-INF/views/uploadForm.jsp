<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>파일 업로드 결과</title>
    <style>
        .profile {
            width: 200px; /* 프로필 이미지의 가로 크기 */
            height: 200px; /* 프로필 이미지의 세로 크기 */
            background-size: cover; /* 이미지가 요소를 덮도록 설정 */
            background-position: center; /* 이미지가 중앙에 위치하도록 설정 */
            border-radius: 50%; /* 원형으로 만들기 */
            border: 2px solid #ddd; /* 이미지의 테두리 스타일 */
        }
    </style>
</head>
<body>
    <div class="profile" style="background-image: url('${absolutePath}');">프로필</div>
    <p>${absolutePath}</p>
</body>
</html>