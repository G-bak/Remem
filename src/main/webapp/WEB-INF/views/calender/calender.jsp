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
	href="https://fonts.googleapis.com/css2?family=Anek+Devanagari:wght@100..800&family=Black+Han+Sans&family=Do+Hyeon&family=Dongle&family=Gamja+Flower&family=Montserrat:ital,wght@0,100..900;1,100..900&family=Nanum+Gothic:wght@400;800&family=Noto+Sans+KR:wght@300&family=Orbit&display=swap"
	rel="stylesheet">
<!-- Font Awesome CDN -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
	integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
	crossorigin="anonymous" referrerpolicy="no-referrer">
    </script>
<title>동적 캘린더</title>
<style>
* {
	user-select: none;
}

body {
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	background-color: #f0f0f0;
}

.calendar {
	position: relative;
	max-width: 850px;
	width: 100%;
	height: 100%;
	background-color: white;
	border-radius: 16px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	overflow: hidden;
}

.calendar-container {
	position: relative;
	max-width: 800px;
	width: 90%;
	height: 90%;
	background-color: white;
	border-radius: 16px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	overflow: hidden;
}

.calendar-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px;
	background-color: white;
	height: 10%;
}

.calendar-header h2 {
	margin: 0;
	font-size: 1.8rem;
	font-weight: 600;
	font-family: "Anek Devanagari", sans-serif;
}

.calendar-header .nav-button {
	font-size: 1.3rem;
	cursor: pointer;
	background-color: transparent;
	border: none;
	outline: none;
	font-weight: bold;
}

.calendar-weekdays {
	display: flex;
	background-color: #f9f9f9;
	font-weight: bold;
	text-align: center;
	height: 8%;
	justify-content: center;
	align-items: center;
}

.weekday {
	flex: 1;
	color: #888;
	font-size: 1.2rem;
	text-align: center;
	line-height: 49px;
	font-family: "Noto Sans KR", sans-serif;
	font-weight: 600;
}

.sunday {
	color: #ff4d4d;
}

.saturday {
	color: #4d88ff;
}

.calendar-days {
	display: flex;
	flex-wrap: wrap;
	height: 80%;
}

.calendar-days>div {
	width: 14.28%;
	height: 20%;
	text-align: center;
	padding: 10px 0;
	box-sizing: border-box;
	position: relative;
	border-bottom: 1px solid #ddd;
	overflow-y: auto;
}

.calendar-days>div.day {
	color: #333;
	cursor: pointer;
	font-family: "Nanum Gothic", sans-serif;
	font-weight: 400;
	font-size: 0.9rem;
}

.calendar-days>div.day:hover {
	background-color: #f0f0f0;
}

.calendar-days>div.sunday {
	color: #ff4d4d;
}

.calendar-days>div.saturday {
	color: #4d88ff;
}

.event {
	background-color: rgba(255, 192, 203, 0.5);
	border-radius: 10px;
	margin: 3px 5px;
	font-size: 0.75rem;
	padding: 3px 0;
	color: #585858;
	text-align: center;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	position: relative;
	font-family: "Nanum Gothic", sans-serif;
	font-weight: 400;
}

.today {
	background-color: #f0f0f0;
}

#scheduleModal {
	position: absolute;
	top: 40%;
	left: 50%;
	transform: translate(-50%, -40%);
	border-radius: 10px;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	z-index: 1000;
	display: none;
	background-color: #fff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	width: 47%;
}

#saveSchedule, #closeModal {
	padding: 11px 20px;
	border-radius: 5px;
	border: none;
	background-color: #007bff;
	color: white;
	font-size: 16px;
	line-height: 16px;
	cursor: pointer;
	margin: 5px;
	font-family: "Noto Sans KR", sans-serif;
	font-weight: 600;
}

#closeModal {
	background-color: #6c757d;
}

.calender-add-title {
	margin-bottom: 7px;
	font-size: 1.3rem;
	font-weight: 900;
	width: 100%;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
	text-align: center;
	background-color: rgb(193, 226, 237);
	padding-top: 21px;
	padding-bottom: 14px;
	line-height: 1.3rem;
	color: #494949;
}

.calender-input-container {
	position: relative;
	width: 85%;
	margin-top: 20px;
	font-size: 1.2rem;
	font-family: "Noto Sans KR", sans-serif;
	font-weight: 600;
}

.calender-friend-container {
	position: relative;
	width: 85%;
	margin-top: 20px;
	font-family: "Noto Sans KR", sans-serif;
	font-weight: 600;
}

.calender-input-container span {
	color: rgb(155, 155, 155);
}

.calender-input-container input {
	position: absolute;
	left: 50%;
	top: 59%;
	transform: translate(-50%, -50%);
	border-style: none;
	background-color: #f0f0f0;
	width: 60%;
	height: 90%;
	padding-left: 5px;
	text-align: center;
}

.calender-btn-container {
	margin: 20px 0;
}

.calender-friend-container .title {
	color: rgb(155, 155, 155);
	font-size: 1.2rem;
	line-height: 1.2rem;
}

.calender-friend-container .name {
	position: absolute;
	left: 50%;
	top: 50%;
	transform: translate(-50%, -50%);
	font-size: 1.3rem;
	line-height: 1.3rem;
	font-weight: 400;
	font-family: "Gamja Flower", sans-serif;
	padding-top: 5px;
	color: #333;
}

/* 자동완성 리스트 스타일 */
.autocomplete-list {
	position: absolute;
	background-color: white;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	max-height: 150px;
	overflow-y: auto;
	z-index: 1000;
	width: 53%;
	top: 161px;
	left: 50%;
	transform: translateX(-50%);
	display: none;
}

.autocomplete-item {
	padding: 8px;
	cursor: pointer;
	font-size: 14px;
}

.autocomplete-item:hover {
	background-color: #f0f0f0;
}

#scheduleDetailModal {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	border-radius: 10px;
	z-index: 1000;
	display: none;
	background-color: #fff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	width: 80%;
	height: 60%;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
}

.schedule-detil-flexBox {
	width: 33%;
	height: 100%;
}

.friend-list-container {
	width: 23%;
}

.friend-list-title-box {
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1.7rem;
	background-color: rgb(124, 124, 124);
	color: rgb(255, 255, 255);
	width: 100%;
	height: 13%;
	display: flex;
	align-items: last baseline;
	overflow-x: auto;
	white-space: nowrap;
}

.colorful-crown {
	color: #FFD700;
	background: linear-gradient(45deg, #FF8C00, #FFD700, #FF4500, #FF69B4, #1E90FF);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	font-size: 1.4rem; /* 아이콘 크기 */
}

.friend-list-name-box {
	width: 100%;
	height: 87%;
	background-color: #e6e6e6;
	overflow: auto;
}

.friend-list-name-box div {
	border-bottom: 1px solid rgb(202, 202, 202);
	padding-top: 7px;
	overflow-x: auto;
	white-space: nowrap;
}

.friend-name {
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1.1rem;
}

.participant {
	font-size: 0.9rem;
}

.memo-container {
	width: 27%;
}

.memo-title-box {
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1.7rem;
	background-color: rgb(255, 226, 154);
	color: rgb(233, 150, 150);
	width: 100%;
	height: 13%;
	display: flex;
	align-items: last baseline;
	overflow-x: auto;
	white-space: nowrap;
}

.colorful-check {
	color: rgb(233, 150, 150);
	font-size: 1.3rem;
}

.memo-content-box {
	width: 100%;
	height: 87%;
	background-color: #fff2c5;
	overflow: auto;
	position: relative;
	font-family: "Noto Sans KR", sans-serif;
	font-weight: 600;
}

.memo-content-box .promise-time {
	padding-top: 7px;
	position: relative;
}

.memo-content-box .promise-time-date {
	overflow-x: auto;
	white-space: nowrap;
}

.memo-content-box .promise-time span {
	color: rgb(155, 155, 155);
	font-size: 0.9rem;
	font-family: "Noto Sans KR", sans-serif;
	font-weight: 600;
}

.memo-content-box .promise-time .date-text {
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1.2rem;
	color: rgb(80, 80, 80);
}

.memo-content-box .promise-time input {
	position: absolute;
	right: 0%;
	top: 100%;
	transform: translateY(-95%);
	border-style: none;
	background-color: #fff2c5;
	width: 65%;
	margin-top: 1.5px;
	padding-left: 3px;
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1.2rem;
	color: rgb(80, 80, 80);
}

.memo-content-box textarea {
	width: 95%;
	height: 75%;
	position: relative;
	left: 50%;
	transform: translateX(-50%);
	border-style: none;
	background-color: #fff2c5;
	padding: 3px;
	box-sizing: border-box;
	resize: none;
	color: rgb(80, 80, 80);
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1rem;
	margin-top: 7px;
}

.diary-container {
	width: 50%;
}

.diary-title-box {
	position: relative;
	width: 100%;
	height: 13%;
	display: flex;
	align-items: last baseline;
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1.7rem;
	background-color: #c7b4ae;
	color: #83522e;
	overflow-x: auto;
	white-space: nowrap;
}

.colorful-book {
	background: linear-gradient(45deg, #83522e, #9c5f43, #83522e);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	font-size: 1.5rem; /* 아이콘 크기 */
	display: inline-block;
}

.diary-content-box {
	width: 100%;
	height: 87%;
	background-color: #e4d9d5;
	overflow: auto;
	position: relative;
	font-family: "Noto Sans KR", sans-serif;
	font-weight: 600;
}

.diary-content-box .content-title {
	position: relative;
	height: 14%;
	overflow-x: auto;
	white-space: nowrap;
}

.diary-content-box .content-title input {
	position: absolute;
	left: 50%;
	top: 50%;
	transform: translate(-50%, -50%);
	border-style: none;
	background-color: #e4d9d5;
	width: 95%;
	margin-top: 1.5px;
	padding-left: 3px;
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1.2rem;
	text-align: center;
	color: rgb(80, 80, 80);
	overflow-x: auto;
	white-space: nowrap;
}

.diary-content-box textarea {
	width: 95%;
	height: 72%;
	position: relative;
	left: 50%;
	transform: translateX(-50%);
	border-style: none;
	background-color: #e4d9d5;
	padding: 2px 4px;
	box-sizing: border-box;
	resize: none;
	color: rgb(80, 80, 80);
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	font-size: 1rem;
}

.btn-calender-detil {
	position: absolute;
	padding: 6px 8px;
	border-radius: 5px;
	border: none;
	color: white;
	font-size: 1.1rem;
	line-height: 1.1rem;
	cursor: pointer;
	font-family: "Gamja Flower", sans-serif;
	font-weight: 400;
	z-index: 1000;
}

.btn-calender-detil:hover {
	text-decoration: underline;
}

.btn-calender-detil-save {
	top: 50%;
	padding: 6px 3px;
	transform: translateY(-50%);
	right: 62px;
	color: #8fff51;
	font-weight: 400;
	font-size: 1.2rem;
}

.btn-calender-detil-myDiary-save {
	bottom: 7px;
	left: 7px;
	font-size: 1.2rem;
	line-height: 1.2rem;
	color: #75a6e7;
}

.btn-calender-detil-close {
	top: 50%;
	transform: translateY(-50%);
	right: 10px;
	color: #fff;
}

.btn-calender-detil-delete {
	bottom: 7px;
	right: 7px;
	font-size: 1.2rem;
	line-height: 1.2rem;
	color: #ec5237;
}

.return-home {
	position: absolute;
	bottom: 49px;
	left: 40px;
	font-size: 27px;
	color: gray;
}
</style>
</head>
<body>
	<div class="calendar-container">
		<div class="calendar">
			<div class="calendar-header">
				<button class="nav-button" id="prevMonth">&nbsp;◀</button>
				<h2 id="monthYear"></h2>
				<button class="nav-button" id="nextMonth">▶&nbsp;</button>
			</div>
			<div class="calendar-weekdays">
				<div class="weekday sunday">일</div>
				<div class="weekday">월</div>
				<div class="weekday">화</div>
				<div class="weekday">수</div>
				<div class="weekday">목</div>
				<div class="weekday">금</div>
				<div class="weekday saturday">토</div>
			</div>
			<div class="calendar-days" id="calendar-days"></div>
		</div>

		<!-- 일정 추가를 위한 모달 -->
		<div id="scheduleModal">
			<div class="calender-add-title">일정 추가</div>
			<div class="calender-input-container">
				<span>일정 </span><input type="text" id="scheduleInput"
					placeholder="일정을 추가하세요" spellcheck="false">
			</div>
			<div class="calender-input-container">
				<span>친구 </span><input type="text" id="scheduleInputFriends"
					placeholder="친구 이름을 검색하세요" spellcheck="false">
			</div>
			<div id="autocompleteFriends" class="autocomplete-list"></div>
			<!-- 자동완성 리스트 -->
			<div class="calender-btn-container">
				<button id="saveSchedule" onclick="">저장</button>
				<button id="closeModal">취소</button>
			</div>
		</div>
		<!-- 일정 추가를 위한 모달 -->
		<div id="scheduleDetailModal">
			<div class="schedule-detil-flexBox friend-list-container">
				<div class="friend-list-title-box">
					&nbsp;<i class="fas fa-crown colorful-crown"></i>&nbsp;<span>null</span>
				</div>
				<div class="friend-list-name-box">
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								1&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">박이번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								2&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">강삼번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								3&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">최사번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								4&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">정오번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								5&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">정육번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								6&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">서칠번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								7&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">유팔번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								8&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">계구번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								9&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">최십번</span>
					</div>
					<div>
						&nbsp;<span class="calender-input-container participant"><span>참석자
								10&nbsp;&nbsp;&nbsp;</span></span><span class="friend-name">최재빈</span>
					</div>
				</div>
			</div>
			<div class="schedule-detil-flexBox memo-container">
				<div class="memo-title-box">
					&nbsp;<span class="colorful-check">✔</span>&nbsp;메모
				</div>
				<div class="memo-content-box">
					<div class="memo-content-flexBox">
						<div class="promise-time promise-time-date">
							<span>&nbsp;&nbsp;날짜&nbsp;&nbsp;&nbsp;&nbsp;</span><span
								class="date-text"></span>
						</div>
						<div class="promise-time">
							<span>&nbsp;&nbsp;시간</span><input id="promiseTime" type="text"
								placeholder="ex) AM 9:00" spellcheck="false">
						</div>
					</div>
					<textarea id="memoContent" name="" placeholder="# 장소"
						spellcheck="false"></textarea>
				</div>
			</div>
			<div class="schedule-detil-flexBox diary-container">
				<div class="diary-title-box">
					&nbsp;<i class="fas fa-book colorful-book"></i>&nbsp;일기장
					<div class="btn-calender-detil btn-calender-detil-save"
						onclick="updateCalenderDetail(this)">저장</div>
					<div class="btn-calender-detil btn-calender-detil-close"
						onclick="calenderDetailColse(this)">닫기</div>
				</div>
				<div class="diary-content-box">
					<div class="content-title">
						<input id="diaryTitle" type="text" placeholder="제목"
							spellcheck="false">
					</div>
					<textarea id="diaryContent" name="" placeholder=""
						spellcheck="false"></textarea>
					<div class="btn-calender-detil btn-calender-detil-myDiary-save"
						onclick="saveMyDiary(this)">내 일기장에 저장</div>
					<div class="btn-calender-detil btn-calender-detil-delete"
						onclick="calenderDetailDelete()">일정삭제</div>
				</div>
			</div>
		</div>
	</div>


	<div class="return-home" onclick="location.href='/main'" style="cursor: pointer;">
		<i class="fa-solid fa-arrow-rotate-left"></i>
	</div>

	<script>
        const userId = '${userId}';
        let userName;
        let ajaxResult = false;

        selectUserName();
        loadAllData();

        let currentYear = 2024;
        let currentMonth = 7; // 8월 (0부터 시작하므로 7)
        let selectedDate; // 선택된 날짜를 저장할 변수

        const holidays = {
            "2024-01-01": "새해 첫날",
            "2024-02-09": "설날연휴",
            "2024-02-10": "설날",
            "2024-02-11": "설날연휴",
            "2024-02-12": "대체공휴일",
            "2024-03-01": "삼일절",
            "2024-04-10": "22대 국회의원 선거",
            "2024-05-05": "어린이날",
            "2024-05-06": "대체공휴일",
            "2024-05-15": "부처님 오신 날",
            "2024-10-09": "한글날",
            "2024-06-06": "현충일",
            "2024-08-15": "광복절",
            "2024-09-16": "추석연휴",
            "2024-09-17": "추석",
            "2024-09-18": "추석연휴",
            "2024-10-03": "개천절",
            "2024-10-09": "한글날",
            "2024-12-25": "성탄절"
        };

        // 기타 초기화
        const schedules = {};
         // dataId를 저장할 객체 추가
        const dataIds = {};
         // 전역 변수로 빈 배열 선언
        let friendsArray = [];
         // 클릭한 일정의 data-id를 저장할 변수
        let selectedEventId = null;

        async function selectUserName() {
            let selectJsonData = {
                'userId': userId
            };

            let selectJsonDataString = JSON.stringify(selectJsonData);

            try {
                const user = await ajaxSelectUserName(selectJsonDataString);
                // console.log("User: " + user);
                if (user) {
                    userName = user;
                } else {
                    console.log("유저 데이터가 없습니다.");
                }
            } catch (error) {
                console.error("AJAX 요청 중 오류 발생:", error);
            }
        }

        async function loadAllData() {
            let selectJsonData = {
                'userId': userId
            };

            let selectJsonDataString = JSON.stringify(selectJsonData);

            try {
                const calendarArray = await ajaxLoadAllData(selectJsonDataString);
                if (calendarArray.length !== 0) {
                    calendarArray.forEach(calender => {
                        const date = calender.calenderDate;
                        const title = calender.calenderTitle;
                        const dataId = calender.dataId;

                        // 해당 날짜에 이미 일정이 존재하는 경우 배열에 추가, 아니면 새로운 배열 생성
                        if (!schedules[date]) {
                            schedules[date] = [];
                            dataIds[date] = []; // 날짜별로 dataId를 저장할 배열 생성
                        }

                        schedules[date].push(title);
                        dataIds[date].push(dataId); // dataId 추가
                    });
                } else {
                    console.log("일정 데이터가 없습니다.");
                }
                // 현재 월 표시
                generateCalendar(currentYear, currentMonth);
            } catch (error) {
                console.error("AJAX 요청 중 오류 발생:", error);
            }
        }

        // 캘린더 생성 시 일정 표시
        function generateCalendar(year, month) {
            const calendarDays = document.getElementById('calendar-days');
            calendarDays.innerHTML = ''; // 이전 내용을 초기화

            const monthYearLabel = document.getElementById('monthYear');
            monthYearLabel.textContent = year + "." + ((month + 1).toString().padStart(2, '0')); // 월을 두 자리로 표시

            const firstDay = new Date(year, month, 1).getDay(); // 해당 월의 첫 날의 요일
            const daysInMonth = new Date(year, month + 1, 0).getDate(); // 해당 월의 일 수

            // 빈 칸 채우기
            for (let i = 0; i < firstDay; i++) {
                const emptyDay = document.createElement('div');
                calendarDays.appendChild(emptyDay);
            }

            // 날짜 추가
            for (let i = 1; i <= daysInMonth; i++) {
                const dayElement = document.createElement('div');
                dayElement.classList.add('day');

                // data-id 속성에 해당 날짜를 설정
                const dateId = year + "-" + ((month + 1).toString().padStart(2, '0')) + "-" + i.toString().padStart(2, '0');
                dayElement.setAttribute('data-id', dateId);

                // 주말에 색상 적용
                const currentDayOfWeek = (i + firstDay - 1) % 7;
                if (currentDayOfWeek === 0) {
                    dayElement.classList.add('sunday');
                } else if (currentDayOfWeek === 6) {
                    dayElement.classList.add('saturday');
                }

                // 공휴일 확인 및 표시
                if (holidays[dateId]) {
                    dayElement.style.color = '#ff4d4d'; // 공휴일 글씨 색 빨간색
                    dayElement.classList.add('holiday'); // 공휴일 스타일 적용
                    dayElement.textContent = holidays[dateId]; // 공휴일 이름으로 변경
                } else {
                    dayElement.textContent = i;
                }

                // 오늘 날짜 강조
                const today = new Date();
                if (year === today.getFullYear() && month === today.getMonth() && i === today.getDate()) {
                    dayElement.classList.add('today');
                }

                // 날짜 클릭 시 일정 추가 모달 표시
                dayElement.addEventListener('click', function() {
                    selectedDate = dateId; // 선택된 날짜를 저장
                    document.getElementById('scheduleModal').style.display = 'flex'; // 모달 열기
                    document.getElementById('scheduleInput').focus(); // 입력창에 포커스
                    selectFriends();
                });

                // 일정이 있는 경우 표시
                const scheduleKey = dateId;
                if (schedules[scheduleKey]) {
                    schedules[scheduleKey].forEach((schedule, index) => {
                        const eventElement = document.createElement('div');
                        eventElement.classList.add('event');
                        eventElement.setAttribute('data-id', dataIds[scheduleKey][index]); // 해당하는 dataId를 설정
                        eventElement.textContent = schedule;

                        // 클릭 이벤트에 핸들러 연결
                        eventElement.addEventListener('click', function() {
                            event.stopPropagation(); // 부모 요소 클릭 이벤트 방지
                            document.getElementById('scheduleDetailModal').style.display = 'flex';
                            selectedEventId = eventElement.getAttribute('data-id');
                            showFriendList(selectedEventId, dateId);
                        });

                        dayElement.appendChild(eventElement);
                    });
                }

                calendarDays.appendChild(dayElement);
            }
        }

        async function selectFriends() {
            let selectJsonData = {
                'userId': userId
            };

            let selectJsonDataString = JSON.stringify(selectJsonData);

            try {
                const friends = await ajaxSelectFriends(selectJsonDataString);
                if (friends.length !== 0) {
                    // 전역 변수 friendsArray에 복사
                    friendsArray = [...friends]; // 스프레드 연산자를 사용하여 배열 복사
                    // console.log(friendsArray);
                } else {
                    console.log("친구 데이터가 없습니다.");
                }
            } catch (error) {
                console.error("AJAX 요청 중 오류 발생:", error);
            }
        }

        document.getElementById('scheduleInputFriends').addEventListener('focus', function() {
            showAllFriends(); // 처음 클릭 시 전체 친구 목록 표시
        });

        document.getElementById('scheduleInputFriends').addEventListener('input', function() {
            const input = this.value.toLowerCase(); // 입력된 값을 소문자로 변환
            const autocompleteList = document.getElementById('autocompleteFriends');
            autocompleteList.innerHTML = ''; // 기존 리스트 초기화

            let filteredFriends;

            if (input.length > 0) {
                // 입력값이 있을 때는 필터링된 친구 목록 표시 (이름 또는 ID로 검색)
                filteredFriends = friendsArray.filter(friend => 
                    friend.userName.toLowerCase().startsWith(input) || friend.friendId.toLowerCase().startsWith(input)
                );
            } else {
                // 입력값이 없을 때는 모든 친구 목록 표시
                filteredFriends = friendsArray;
            }

            if (filteredFriends.length > 0) {
                autocompleteList.style.display = 'block';
                filteredFriends.forEach(function(friend) {
                    const item = document.createElement('div');
                    item.classList.add('autocomplete-item');
                    item.textContent = friend.userName + " (" + friend.friendId + ")";

                    item.addEventListener('click', function() {
                        // 선택된 친구를 참석자 목록에 추가
                        const container = document.getElementById('scheduleModal');
                        const attendeeContainers = container.querySelectorAll('.calender-friend-container');
                        const attendeeCount = attendeeContainers.length + 1; // 참석자 번호 계산

                        const attendeeContainer = document.createElement('div');
                        attendeeContainer.classList.add('calender-friend-container');
                        attendeeContainer.setAttribute('data-key', friend.friendId); // friendId를 data-key에 저장
                        attendeeContainer.innerHTML = '<span class="title">참석자 ' + attendeeCount + ' </span>' +
                                                        '<span class="name">' + friend.userName + '</span>';

                        container.insertBefore(attendeeContainer, container.querySelector('.calender-btn-container'));

                        // 자동완성 리스트 및 입력 필드 초기화
                        autocompleteList.innerHTML = '';
                        document.getElementById('scheduleInputFriends').value = '';
                        autocompleteList.style.display = 'none'; // 선택 후 리스트 숨김
                    });

                    autocompleteList.appendChild(item);
                });
            } else {
                autocompleteList.style.display = 'none'; // 필터링된 친구가 없으면 리스트 숨김
            }
        });

        function showAllFriends() {
            const autocompleteList = document.getElementById('autocompleteFriends');
            autocompleteList.innerHTML = ''; // 기존 리스트 초기화

            if (friendsArray.length > 0) {
                autocompleteList.style.display = 'block';
                friendsArray.forEach(function(friend) {
                    const item = document.createElement('div');
                    item.classList.add('autocomplete-item');
                    item.textContent = friend.userName + " (" + friend.friendId + ")";

                    item.addEventListener('click', function() {
                        // 선택된 친구를 참석자 목록에 추가
                        const container = document.getElementById('scheduleModal');
                        const attendeeContainers = container.querySelectorAll('.calender-friend-container');
                        const attendeeCount = attendeeContainers.length + 1; // 참석자 번호 계산

                        const attendeeContainer = document.createElement('div');
                        attendeeContainer.classList.add('calender-friend-container');
                        attendeeContainer.setAttribute('data-key', friend.friendId); // friendId를 data-key에 저장
                        attendeeContainer.innerHTML = '<span class="title">참석자 ' + attendeeCount + ' </span>' +
                                                        '<span class="name">' + friend.userName + '</span>';

                        container.insertBefore(attendeeContainer, container.querySelector('.calender-btn-container'));

                        // 자동완성 리스트 및 입력 필드 초기화
                        autocompleteList.innerHTML = '';
                        document.getElementById('scheduleInputFriends').value = '';
                        autocompleteList.style.display = 'none'; // 선택 후 리스트 숨김
                    });

                    autocompleteList.appendChild(item);
                });
            } else {
                autocompleteList.style.display = 'none'; // 친구 목록이 없으면 리스트 숨김
            }
        }

        // 전역 클릭 이벤트 리스너 추가
        document.addEventListener('click', function(event) {
            const autocompleteList = document.getElementById('autocompleteFriends');
            const inputField = document.getElementById('scheduleInputFriends');
            
            if (autocompleteList.style.display === 'block' && !inputField.contains(event.target) && !autocompleteList.contains(event.target)) {
                // inputField와 autocompleteList 외의 다른 곳을 클릭하면 자동완성 목록 숨김
                autocompleteList.style.display = 'none';
            }
        });

        async function showFriendList(dataId, date) {
            let selectJsonData = {
                'userId': userId,
                'dataId': dataId
            };

            let selectJsonDataString = JSON.stringify(selectJsonData);

            try {
                const friends = await ajaxShowFriendList(selectJsonDataString);
                if (friends.length !== 0) {
                    // 친구 목록 초기화
                    const friendListContainer = document.querySelector('.friend-list-name-box');
                    friendListContainer.innerHTML = ''; // 기존 친구 목록 초기화

                    // 사용자 이름 설정
                    document.querySelector('.date-text').textContent = date;
                    document.querySelector('.friend-list-title-box span').textContent = friends[0].readerName;
                    document.querySelector('.diary-content-box textarea').placeholder = "# " + userName;

                    // 친구 목록을 동적으로 추가
                    friends.forEach(function(friend, index) {
                        const friendElement = document.createElement('div');
                        friendElement.innerHTML = '&nbsp;<span class="calender-input-container participant">' +
                                                '<span>참석자 ' + (index + 1) + '&nbsp;&nbsp;&nbsp;</span>' +
                                                '</span><span class="friend-name">' + friend.friendName + '</span>';
                        friendListContainer.appendChild(friendElement);
                    });
                } else {
                    console.log("친구 데이터가 없습니다.");
                    // 친구 목록 초기화
                    const friendListContainer = document.querySelector('.friend-list-name-box');
                    friendListContainer.innerHTML = ''; // 기존 친구 목록 초기화

                    // 사용자 이름 설정
                    document.querySelector('.date-text').textContent = date;
                    document.querySelector('.friend-list-title-box span').textContent = userName;
                    document.querySelector('.diary-content-box textarea').placeholder = "# " + userName;
                }
            } catch (error) {
                console.error("AJAX 요청 중 오류 발생:", error);
            }

            let selectJsonData2 = {
                'dataId': dataId
            };
            let selectJsonDataString2 = JSON.stringify(selectJsonData2);
    
            try {
                // AJAX 요청이 성공하면 해당 키워드를 다른 질문 항목으로 이동합니다.
                const calendarDetailObj = await ajaxSelectCalenderDetail(selectJsonDataString2);
                if (calendarDetailObj) {
                    const calenderDetailContainer = document.getElementById('scheduleDetailModal');
                    // console.log(calenderDetailContainer);
                    // console.log("Data ID: " + calendarDetailObj.dataId);
                    const readerTitleContainer = calenderDetailContainer.querySelector('.friend-list-title-box');
                    readerTitleContainer.querySelector('span').textContent = calendarDetailObj.readerId;
                    // console.log("Reader ID: " + calendarDetailObj.readerId);
                    calenderDetailContainer.querySelector('#promiseTime').value = calendarDetailObj.appointmentTime;
                    // console.log("Appointment Time: " + calendarDetailObj.appointmentTime);
                    calenderDetailContainer.querySelector('#memoContent').value = calendarDetailObj.memoContent;
                    // console.log("Memo Content: " + calendarDetailObj.memoContent);
                    calenderDetailContainer.querySelector('#diaryTitle').value = calendarDetailObj.diaryTitle;
                    // console.log("Diary Title: " + calendarDetailObj.diaryTitle);
                    calenderDetailContainer.querySelector('#diaryContent').value = calendarDetailObj.diaryContent;
                    // console.log("Diary Content: " + calendarDetailObj.diaryContent);
                } else {
                    console.log("일정 상세 정보 추가에 실패하였습니다.");
                }
            } catch (error) {
                console.error("AJAX 요청 중 오류 발생:", error);
            }
        }

        async function calenderDetailDelete() {
            const dataId = selectedEventId;
            const eventElement = document.querySelector('[data-id="' + dataId + '"]');

            let deleteJsonData = {
                'dataId': dataId,
                'userId': userId
            };

            let deleteJsonDataString = JSON.stringify(deleteJsonData);

            if (confirm("일정을 삭제하시겠습니까?")) {
                // AJAX 요청을 통해 서버에서 데이터 삭제
                try {
                    // AJAX 요청이 성공하면 질문 항목을 질문 목록에 추가합니다.
                    const ajaxResult = await ajaxDeleteCalender(deleteJsonDataString);
                    if (ajaxResult) {
                        // console.log("일정이 성공적으로 삭제되었습니다.");
                        document.getElementById('scheduleDetailModal').style.display = 'none';
                        eventElement.remove(); // HTML 요소 삭제
                    } else {
                        console.log("일정 삭제 실패: " + response.header.resultMessage);
                    }
                } catch (error) {
                    // AJAX 요청 중 오류가 발생한 경우, 콘솔에 에러 메시지를 출력합니다.
                    console.error("AJAX 요청 중 오류 발생:", error);
                }
            }
        }

        // 일정 저장 버튼 클릭 시
        document.getElementById('saveSchedule').addEventListener('click', saveSchedule);

        // 입력 필드에서 Enter 키를 누를 때 일정 추가
        // document.getElementById('scheduleInput').addEventListener('keypress', function(event) {
        //     if (event.key === 'Enter') {
        //         saveSchedule();
        //     }
        // });

        // 일정 저장 함수 (공통으로 사용)
        async function saveSchedule() {
            const scheduleInput = document.getElementById('scheduleInput').value;
            if (scheduleInput.trim()) {
                // 선택된 날짜의 data-id 값을 가진 요소 찾기
                const dayElement = document.querySelector('.day[data-id=\'' + selectedDate + '\']');

                if (!schedules[selectedDate]) {
                    schedules[selectedDate] = [];
                }
                schedules[selectedDate].push(scheduleInput);

                // 해당 날짜에 일정 추가
                const eventElement = document.createElement('div');
                eventElement.classList.add('event');
                eventElement.textContent = scheduleInput;

                // 일정에 고유한 data-id 부여 (userId + 현재 시간 밀리초)
                const uniqueId = selectedDate + '-' + userId + '-' + new Date().getTime();
                eventElement.setAttribute('data-id', uniqueId);
                dayElement.appendChild(eventElement);

                // 일정 클릭 시 일정 상세 모달창 표시
                eventElement.addEventListener('click', function(event) {
                    event.stopPropagation(); // 부모 요소 클릭 이벤트 방지
                    document.getElementById('scheduleDetailModal').style.display = 'flex';
                    selectedEventId = eventElement.getAttribute('data-id');
                    showFriendList(selectedEventId, selectedDate);
                });

                document.getElementById('scheduleInput').value = ''; // 입력 필드 초기화
                document.getElementById('scheduleModal').style.display = 'none'; // 모달 닫기

                let insertJsonData = {
                    'userId': userId,
                    'dataId': uniqueId,
                    'calenderTitle': scheduleInput,
                    'friendId': [], // friends 배열을 먼저 정의합니다.
                    'friendName': [] // friends 배열을 먼저 정의합니다.
                };

                const container = document.getElementById('scheduleModal');
                const friendsContainers = container.querySelectorAll('.calender-friend-container');

                friendsContainers.forEach(function(friendContainer) {
                    const friendId = friendContainer.getAttribute('data-key'); // data-key에서 friendId 가져오기
                    const friendName = friendContainer.querySelector('.name').textContent.trim();
                    insertJsonData.friendId.push(friendId);
                    insertJsonData.friendName.push(friendName);
                });

                let insertJsonDataString = JSON.stringify(insertJsonData);

                try {
                    const ajaxResult = await ajaxInsertCalender(insertJsonDataString);
                    if (ajaxResult) {
                        // console.log('일정 저장 성공');
                        // 참석자 목록 초기화
                        const container = document.getElementById('scheduleModal');
                        const existingAttendees = container.querySelectorAll('.calender-friend-container');
                        existingAttendees.forEach(function(attendee) {
                            attendee.remove(); // 기존 참석자 모두 제거
                        });
                        let diaryContent = "# " + userName;
                        
                        // AJAX 요청('calender_memo_diary' 테이블에 기본값 insert)
                        let insertJsonData = {
                            'dataId': uniqueId,
                            'readerId': userName,
                            'diaryContent': diaryContent
                        };
                        let insertJsonDataString = JSON.stringify(insertJsonData);

                        try {
                            // AJAX 요청이 성공하면 질문 항목을 질문 목록에 추가합니다.
                            const ajaxResult = await ajaxInsertCalenderDetail(insertJsonDataString);
                            if (ajaxResult) {

                            } else {
                                // console.log("일정 상세 정보 추가 실패: " + response.header.resultMessage);
                            }
                        } catch (error) {
                            // AJAX 요청 중 오류가 발생한 경우, 콘솔에 에러 메시지를 출력합니다.
                            console.error("AJAX 요청 중 오류 발생:", error);
                        }

                    } else {
                        // console.log('일정 저장 실패');
                        // 참석자 목록 초기화
                        const container = document.getElementById('scheduleModal');
                        const existingAttendees = container.querySelectorAll('.calender-friend-container');
                        existingAttendees.forEach(function(attendee) {
                            attendee.remove(); // 기존 참석자 모두 제거
                        });
                    }
                } catch (error) {
                    console.error("AJAX 요청 중 오류 발생:", error);
                }
            }
        }

        async function updateCalenderDetail(button) {
            const calendarDetailContainer = button.closest('#scheduleDetailModal');
            const appointmentTime = calendarDetailContainer.querySelector('#promiseTime').value;
            const memoContent = calendarDetailContainer.querySelector('#memoContent').value;
            const diaryTitle = calendarDetailContainer.querySelector('#diaryTitle').value;
            const diaryContent = calendarDetailContainer.querySelector('#diaryContent').value;

            // AJAX 요청('calender_memo_diary' 테이블에 기본값 update)
            let updateJsonData = {
                'dataId': selectedEventId,
                'appointmentTime': appointmentTime,
                'memoContent': memoContent,
                'diaryTitle': diaryTitle,
                'diaryContent': diaryContent
            };

            let updateJsonDataString = JSON.stringify(updateJsonData);

            try {
                // AJAX 요청이 성공하면 질문 항목을 질문 목록에 추가합니다.
                const ajaxResult = await ajaxUpdateCalenderDetail(updateJsonDataString);
                if (ajaxResult) {
                    alert("일정 상세 정보가 저장되었습니다");
                } else {
                    // console.log("일정 상세 정보 수정 실패: " + response.header.resultMessage);
                }
            } catch (error) {
                // AJAX 요청 중 오류가 발생한 경우, 콘솔에 에러 메시지를 출력합니다.
                console.error("AJAX 요청 중 오류 발생:", error);
            }
        }

        async function saveMyDiary(button) {
            const calendarDetailContainer = button.closest('#scheduleDetailModal');
            const diaryTitle = calendarDetailContainer.querySelector('#diaryTitle').value;
            const diaryContent = calendarDetailContainer.querySelector('#diaryContent').value;
            const writeDate = calendarDetailContainer.querySelector('.date-text').textContent;

            if (diaryTitle && diaryContent) {
                let insertJsonData = {
                    'userId': userId,
                    'writeDate': writeDate,
                    'diaryTitle': diaryTitle,
                    'diaryContent': diaryContent
                };

                let insertJsonDataString = JSON.stringify(insertJsonData);

                try {
                    const ajaxResult = await ajaxSaveMyDiary(insertJsonDataString);
                    if (ajaxResult) {
                        alert("내 일기장에 추가에 성공하였습니다");
                    } else {
                        // console.log("내 일기장에 추가에 실패하였습니다");
                    }
                } catch (error) {
                    // AJAX 요청 중 오류가 발생한 경우, 콘솔에 에러 메시지를 출력합니다.
                    console.error("AJAX 요청 중 오류 발생:", error);
                }
            } else {
                alert("일기장의 제목 또는 내용을 모두 작성해주세요");
            }
            
        }

        // 일정 추가 모달 닫기 버튼 클릭 시
        document.getElementById('closeModal').addEventListener('click', function() {
            document.getElementById('scheduleModal').style.display = 'none';
            document.getElementById('scheduleInput').value = ''; // 입력 필드 초기화
        });

        // 일정 상세 모달 닫기 버튼 클릭 시
        function calenderDetailColse(button) {
            button.closest('#scheduleDetailModal').style.display = 'none';
        }

        // 이전 달로 이동
        document.getElementById('prevMonth').addEventListener('click', function() {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            generateCalendar(currentYear, currentMonth);
        });

        // 다음 달로 이동
        document.getElementById('nextMonth').addEventListener('click', function() {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            generateCalendar(currentYear, currentMonth);
        });

        function ajaxInsertCalender(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/insertCalender",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        if (response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
                            resolve(true); // 성공 시 프로미스 해결
                        } else {
                            console.log("Result Code: " + response.header.resultCode);
                            console.log("Result Message:", response.header.resultMessage);
                            resolve(false); // 실패 시 프로미스 해결
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxLoadAllData(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/loadAllData",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        // console.log("AJAX Response:", response); // 응답 데이터 확인
                        if (response && response.header && response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
    
                            let responseBody;
                            if (typeof response.body === 'string') {
                                responseBody = JSON.parse(response.body); // 문자열을 객체로 변환
                            } else {
                                responseBody = response.body;
                            }
    
                            if (responseBody && Array.isArray(responseBody.data.calenders)) {
                                // console.log("calenders Array:", responseBody.data.calenders);
                                resolve(responseBody.data.calenders); // 키워드 배열을 반환
                            } else {
                                console.log("No calenders array found in response Body");
                                resolve([]); // 키워드 배열이 없으면 빈 배열 반환
                            }
                        } else {
                            console.log("Invalid response header or result code");
                            resolve([]); // 올바른 응답이 아니면 빈 배열 반환
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxDeleteCalender(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/deleteCalender",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        if (response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
                            resolve(true); // 성공 시 프로미스 해결
                        } else {
                            console.log("Result Code: " + response.header.resultCode);
                            console.log("Result Message:", response.header.resultMessage);
                            resolve(false); // 실패 시 프로미스 해결
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxSelectFriends(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/selectFriends",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        // console.log("AJAX Response:", response); // 응답 데이터 확인
                        if (response && response.header && response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
    
                            let responseBody;
                            if (typeof response.body === 'string') {
                                responseBody = JSON.parse(response.body); // 문자열을 객체로 변환
                            } else {
                                responseBody = response.body;
                            }
    
                            if (responseBody && Array.isArray(responseBody.data.friends)) {
                                // console.log("friends Array:", responseBody.data.friends);
                                resolve(responseBody.data.friends); // 키워드 배열을 반환
                            } else {
                                console.log("No friends array found in response Body");
                                resolve([]); // 키워드 배열이 없으면 빈 배열 반환
                            }
                        } else {
                            console.log("Invalid response header or result code");
                            resolve([]); // 올바른 응답이 아니면 빈 배열 반환
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxShowFriendList(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/showFriendList",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        // console.log("AJAX Response:", response); // 응답 데이터 확인
                        if (response && response.header && response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
    
                            let responseBody;
                            if (typeof response.body === 'string') {
                                responseBody = JSON.parse(response.body); // 문자열을 객체로 변환
                            } else {
                                responseBody = response.body;
                            }
    
                            if (responseBody && Array.isArray(responseBody.data.friends)) {
                                // console.log("friends Array:", responseBody.data.friends);
                                resolve(responseBody.data.friends); // 키워드 배열을 반환
                            } else {
                                console.log("No friends array found in response Body");
                                resolve([]); // 키워드 배열이 없으면 빈 배열 반환
                            }
                        } else {
                            console.log("Invalid response header or result code");
                            resolve([]); // 올바른 응답이 아니면 빈 배열 반환
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxSelectUserName(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/selectUserName",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        if (response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
                            resolve(response.body); // 성공 시 프로미스 해결
                        } else {
                            console.log("Result Code: " + response.header.resultCode);
                            console.log("Result Message:", response.header.resultMessage);
                            resolve(false); // 실패 시 프로미스 해결
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxSelectCalenderDetail(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/selectCalenderDetail",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        if (response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);

                            let responseBody;
                                if (typeof response.body === 'string') {
                                    responseBody = JSON.parse(response.body); // 문자열을 객체로 변환
                                } else {
                                    responseBody = response.body;
                                }
                            resolve(responseBody.data.calenderInfo); // 성공 시 프로미스 해결
                        } else {
                            console.log("Result Code: " + response.header.resultCode);
                            console.log("Result Message:", response.header.resultMessage);
                            resolve(false); // 실패 시 프로미스 해결
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxInsertCalenderDetail(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/insertCalenderDetail",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        if (response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
                            resolve(true); // 성공 시 프로미스 해결
                        } else {
                            console.log("Result Code: " + response.header.resultCode);
                            console.log("Result Message:", response.header.resultMessage);
                            resolve(false); // 실패 시 프로미스 해결
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxUpdateCalenderDetail(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/calender/updateCalenderDetail",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        if (response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
                            resolve(true); // 성공 시 프로미스 해결
                        } else {
                            console.log("Result Code: " + response.header.resultCode);
                            console.log("Result Message:", response.header.resultMessage);
                            resolve(false); // 실패 시 프로미스 해결
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }

        function ajaxSaveMyDiary(JsonData) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/saveMyDiary",
                    headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    dataType: 'json',
                    data: JsonData,
                    success: function (response) {
                        if (response.header.resultCode === '00') {
                            console.log("Response Code: " + response.header.resultCode);
                            console.log("Response Message:", response.header.resultMessage);
                            resolve(true); // 성공 시 프로미스 해결
                        } else {
                            console.log("Result Code: " + response.header.resultCode);
                            console.log("Result Message:", response.header.resultMessage);
                            resolve(false); // 실패 시 프로미스 해결
                        }
                    },
                    error: function (error) {
                        console.error("Error:", error);
                        reject(error); // 오류 시 프로미스 거부
                    }
                });
            });
        }
    </script>
</body>
</html>