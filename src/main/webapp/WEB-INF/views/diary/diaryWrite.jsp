<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Dongle&family=Gamja+Flower&family=Nanum+Gothic:wght@400;800&family=Noto+Sans+KR:wght@300&family=Orbit&display=swap"
	rel="stylesheet">
<title>일기 작성</title>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700&display=swap')
	;

body {
	font-family: 'Nanum Gothic', sans-serif;
	background-color: #bae19f;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	box-sizing: border-box;
}

input, textarea {
	outline: none;
}

::-webkit-scrollbar {
	width: 10px;
	height: 10px;
}

::-webkit-scrollbar-track {
	background: #f0f0f0;
	border-radius: 10px;
}

::-webkit-scrollbar-thumb {
	background-color: #83836721;
	border-radius: 10px;
}

::-webkit-scrollbar-corner {
	background-color: #f0f0f0;
}

.diary-container {
	background-color: #ffffff;
	border-radius: 15px;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
	padding: 40px;
	width: 80%;
	height: 90%;
	box-sizing: border-box;
	position: relative;
	overflow: hidden;
}

.diary-container h1 {
	text-align: center;
	color: #333;
	font-size: 32px;
	margin-bottom: 20px;
}

.diary-container label {
	display: block;
	color: #555;
	font-size: 18px;
}

.diary-container input[type="text"] {
	position: relative;
	left: 50%;
	transform: translateX(-50%);
	width: 75%;
	height: 12%;
	padding: 10px;
	border: 1px solid #ccc;
	border-style: none;
	border-radius: 5px;
	font-size: 2.3rem;
	line-height: 2.3rem;
	box-sizing: border-box;
	text-align: center;
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-style: normal;
}

.diary-date-container {
	height: 8%;
	display: flex;
	align-items: center;
}

.diary-container input[type="date"] {
	width: 150px;
	/* padding: 7px 10px; */
	/* border: 1px solid #ccc; */
	border-style: none;
	border-radius: 5px;
	font-family: 'Nanum Gothic', sans-serif;
	/* color: #747474; */
	box-sizing: border-box;
	user-select: none;
	background: #fff;
	font-weight: 400;
	font-style: normal;
	font-size: 1.5rem;
	user-select: none;
	font-family: "Gamja Flower", sans-serif;
	color: #7e7e7e;
}

.diary-container textarea {
	width: 100%;
	height: 80%;
	border-radius: 5px;
	font-family: "Gamja Flower", sans-serif;
	font-size: 16px;
	box-sizing: border-box;
	resize: none;
	border-style: none;
	font-size: 1.5rem;
	font-weight: 400;
	color: #555;
	padding: 20px 10px;
}

.diary-container input[type="submit"] {
	display: block;
	width: 100px;
	height: 40px;
	background-color: #aeaeae;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 18px;
	cursor: pointer;
	transition: background-color 0.3s;
	position: absolute;
	top: 20px;
	right: 20px;
	line-height: 40px;
	user-select: none;
	font-family: "Gamja Flower", sans-serif;
}

.diary-container input[type="submit"]:hover {
	background-color: #45a049;
}

.back-button {
	display: block;
	position: absolute;
	top: 20px;
	left: 20px;
	background-color: #aeaeae;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 18px;
	cursor: pointer;
	text-align: center;
	transition: background-color 0.3s;
	user-select: none;
	width: 100px;
	height: 40px;
	line-height: 40px;
	z-index: 100;
	padding: 1px 1px;
	font-family: "Gamja Flower", sans-serif;
}

.back-button:hover {
	background-color: #45a049;
}

.date-text span {
	font-weight: 400;
	font-style: normal;
	font-size: 1.5rem;
	user-select: none;
	font-family: "Gamja Flower", sans-serif;
	color: #555;
}

.temporary-storage {
	display: block;
	width: 100px;
	height: 40px;
	background-color: #aeaeae;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 18px;
	cursor: pointer;
	transition: background-color 0.3s;
	position: absolute;
	top: 75px;
	right: 20px;
	line-height: 40px;
	user-select: none;
	font-family: "Gamja Flower", sans-serif;
}

.temporary-storage:hover {
	background-color: #45a049;
}

#diaryForm {
	width: 100%;
	height: 100%;
}
</style>
</head>
<body>
	<div class="diary-container">
		<div class="back-button" onclick="location.href='/main'">돌아가기</div>
		<form id="diaryForm" action="/diarySave" method="post">
			<input class="title" type="text" id="diaryTitle" name="diaryTitle"
				placeholder="제목" spellcheck="false" required>

			<div class="diary-date-container">
				<label for="diaryDate" class="date-text"><span>날짜&nbsp;&nbsp;&nbsp;</span></label>
				<input type="date" id="diaryDate" name="writeDate" readonly>
			</div>

			<textarea id="diaryEntry" name="diaryContent"
				placeholder="오늘의 일기를 작성하세요..." spellcheck="false" required></textarea>

			<button type="button" class="temporary-storage" onclick="saveDiary()">임시
				저장</button>
			<input id="btn_diary" type="submit" value="작성완료">
		</form>
	</div>

	<script>
		document
				.getElementById('diaryForm')
				.addEventListener(
						'submit',
						function(event) {
							const title = document.getElementById('diaryTitle').value;
							const content = document
									.getElementById('diaryEntry').value;

							if (!title || !date || !content) {
								event.preventDefault(); // 페이지 이동 막기
								alert('모든 필드를 작성해주세요.');
							}
						});

		function saveDiary() {
			const diaryTitle = document.getElementById('diaryTitle').value;
			const diaryEntry = document.getElementById('diaryEntry').value;
			localStorage.setItem('diaryTitle', diaryTitle);
			localStorage.setItem('diaryEntry', diaryEntry);
			alert('일기가 임시저장되었습니다.');
		}

		// 현재 날짜와 시간을 설정하는 함수
		function setDateTime() {
			const now = new Date();
			const year = now.getFullYear();
			const month = String(now.getMonth() + 1).padStart(2, '0');
			const day = String(now.getDate()).padStart(2, '0');

			// 날짜 설정
			const currentDate = year + '-' + month + '-' + day;
			document.getElementById('diaryDate').value = currentDate;
		}

		window.onload = function() {
			setDateTime(); // 페이지 로드 시 현재 날짜와 시간을 설정

			const savedDiaryTitle = localStorage.getItem('diaryTitle');
			const savedDiaryEntry = localStorage.getItem('diaryEntry');

			if (savedDiaryTitle) {
				document.getElementById('diaryTitle').value = savedDiaryTitle;
			}

			if (savedDiaryEntry) {
				document.getElementById('diaryEntry').value = savedDiaryEntry;
			}
		}
	</script>
</body>
</html>
