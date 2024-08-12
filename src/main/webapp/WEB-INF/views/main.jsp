<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
	integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
	crossorigin="anonymous" referrerpolicy="no-referrer">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<link rel="stylesheet"
	href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/main.js"></script>


<link href="/css/main.css" rel="stylesheet">
<title>마이페이지</title>
</head>

<body>
	<header id="header">
		<span>#오늘 일기</span>
		<div class="icon-container">
			<a href="#" id="profile-icon"><i class="far fa-user-circle"></i></a>
			<a href="#" id="addfriend-icon"><i class="fas fa-user-plus"></i></a>
			<button id="requestFriend">친구신청확인</button>
		</div>
	</header>


	<div id="friendRequestPopup" class="popupfriendRequest">
		<div class="popup-friendRequest-content">
			<p>친구 신청이 확인되었습니다!</p>
			<ul class="friend-request-list">
				<!--                 <li class="friend-request-item"><p>친구 신청 1</p></li> -->
				<!--                 <li class="friend-request-item"><p>친구 신청 2</p></li> -->
				<!--                 <li class="friend-request-item"><p>친구 신청 3</p></li> -->
			</ul>
			<button class="popup-friendRequest-close"
				id="popupfriendRequestCloseBtn">닫기</button>
		</div>
	</div>







	<div class="popup" id="profile-popup">
		<div class="popup-content">
			<h2>프로필 팝업창</h2>
			<div class="profile-container">
				<div class="photo">사진</div>
				<div>
					<p>팔로워</p>
					<p class="follower">63</p>
				</div>
				<div>
					<p>팔로잉</p>
					<p class="following">63</p>
				</div>
			</div>
			<div class="profile-introduce">
				<p class="nickname">${user.userName}</p>
				<p class="profile-id">@ ${user.userId}</p>
				<p>반가워! 나는 상큼한 자두같은 아이야</p>
			</div>
			<div class="ModifyPage">
				<a href="#" class="addressModify" id="open-address-popup">주소 수정</a>
				<a href="#" class="passwordModify" id="open-password-popup">비밀번호
					수정</a>
			</div>
			<button class="close-btn" id="close-profile-popup">닫기</button>
		</div>
	</div>

	<div class="address-popup" id="address-popup">
		<div class="address-content">
			<h2>주소 수정 팝업창</h2>
			<form action="/user/modifyAddress" method="post"
				id="frm-modifyAddress">
				<div class="frm-modifyAddress-body">
					<input type="hidden" id="sample6_postcode" placeholder="우편번호">
					<input type="text" id="sample6_address" name="userAddress"
						value="${user.userAddress}"><br> <i
						class="fa-solid fa-magnifying-glass" id="search-icon"
						onclick="sample6_execDaumPostcode()"></i></input><br> <input
						type="hidden" id="sample6_detailAddress" placeholder="상세주소">
					<input type="hidden" id="sample6_extraAddress" placeholder="참고항목">
				</div>
				<div class="frm-modifyAddress-footer">
					<button type="submit" class="address">수정</button>
				</div>
			</form>

			<button class="close-btn" id="close-address-popup">닫기</button>

		</div>

	</div>

	<div class="password-popup" id="password-popup">
		<div class="password-content">
			<h2>비밀번호 수정 팝업창</h2>
			<form action="/user/modifyPassword" method="post"
				id="frm-modifyPassword">
				<div class="frm-modifyPassword-body">
					<input type="text" id="pw" name="currentPassword"
						placeholder="현재 비밀번호 입력"><br /> <input type="text"
						id="pw2" name="newPassword" placeholder="변경할 비밀번호 입력"><br />
				</div>
				<div class="frm-modifyPassword-footer">
					<button type="submit" class="password">수정</button>
				</div>
			</form>

			<button class="close-btn" id="close-password-popup">닫기</button>

		</div>
	</div>


	<div class="addfriend-popup" id="addfriend-popup">
		<div class="addfriend-content">
			<h2>친구추가 팝업창</h2>
			<p>여기에는 친구추가 관련 정보를 넣을 수 있습니다.</p>

			<!--             <form onsubmit="return false;" id="frm-addfriend"> -->
			<input type="text" id="name-input" placeholder="아이디 입력"
				oninput="filterFriends()">
			<button type="submit" id="search-btn">
				<i class="fa-solid fa-magnifying-glass" id="search-icon"></i>
			</button>
			<!--             </form> -->
			<table class="addfriend-list" id="addfriend-list">
				<!--             js에서 친구목록 동적 생성 -->

			</table>
			<!-- 			<button class="addfriend-close-btn" id="close-addfriend-popup">닫기</button> -->


			<h2>친구 추천</h2>
			<p>
				친구 추천 목록입니다.<br>아이콘을 눌러서 추천 목록을 갱신할 수 있습니다.
			</p>

			<!--             <form onsubmit="return false;" id="frm-addfriend"> -->

			<button type="submit" id="recommend-search-btn"
				onclick="refreshRecommendFriendList()">
				<i class="fa-solid fa-magnifying-glass" id="search-icon"></i>
			</button>
			<!--             </form> -->
			<table class="recommendfriend-list" id="recommendfriend-list">
				<!-- 				동적으로 친구 추천 목록 생성 -->
			</table>
			<button class="addfriend-close-btn" id="close-addfriend-popup">닫기</button>
		</div>
	</div>

	<section class="section">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-leftslide">
					<button class="menu-btn" data-target="#content-diary">일기장</button>
					<button class="menu-btn" data-target="#content-todo">오늘할일</button>
					<button class="menu-btn" data-target="#content-budget">가계부</button>
					<button class="menu-btn" data-target="#content-capsule">타임캡슐</button>
				</div>
				<div class="swiper-rightslide" id="content-diary">
					<div class="diary-header">
						<a href="/diaryWrite"><button class="insert-btn">
								<i class="fa-regular fa-pen-to-square"></i>
							</button></a>
						<button class="chatbot-btn">
							<i class="fa-regular fa-comments"></i>
						</button>
					</div>
					<div class="diary-container"></div>
					<div class="diary-popup" id="diary-popup">
						<div class="diary-content">
							<h2>일기작성 팝업창</h2>
							<p>여기에는 일기를 작성할 수 있습니다.</p>
							<form action="./main.html" method="post" id="frm-diary">
								<input type="date" id="diary-date" name="diaryDate"
									placeholder="날짜"> <input type="text" id="diary-title"
									name="diaryTitle" placeholder="제목">
								<textarea rows="5" cols="45" id="diary-content"
									name="diaryContent" placeholder="내용"></textarea>
								<button class="diary-close-btn" id="close-diary-popup">닫기</button>
								<button type="submit" class="diary-save-btn"
									id="save-diary-popup">저장</button>
							</form>
						</div>
					</div>
				</div>
				<div class="swiper-rightslide" id="content-todo">
					<div class="wrapper">
						<header>오늘 할 일</header>
						<div class="input-container">
							<input type="text" id="todoInput" placeholder="할 일을 입력하세용">
							<button id="addTodoButton">
								<i class="fa-solid fa-check"></i>
							</button>
						</div>
						<ul id="todoList"></ul>
					</div>
				</div>
				<div class="swiper-rightslide" id="content-budget">
					<div class="accountModify" id="accountModify">
						<div class="accountModify-popup-content">
							<input type="date" class="date" id="input-date">
							<button id="btn_account_save" type="submit">저장</button>
						</div>
					</div>
					<div class="account-book-container">
						<div class="account-book-content">
							<h1>총 수입</h1>
							<div class="total-income">
								<div class="income-item">
									<i class="far fa-money-bill-alt"></i>
									<p>월급</p>
									<input type="text" id="salary"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-won-sign"></i>
									<p>부업</p>
									<input type="text" id="side-job"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-piggy-bank"></i>
									<p>저축</p>
									<input type="text" id="saving"> 원
								</div>
								<div class="income-items">
									<i class="fas fa-dollar-sign"></i>
									<p>총 합계</p>
									<p class="income-total">원</p>
								</div>
							</div>
						</div>
						<div class="account-book-content">
							<h1>총 지출</h1>
							<div class="total-income">
								<div class="income-item">
									<i class="fas fa-utensils"></i>
									<p>식비</p>
									<input type="text" id="food-expenses"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-taxi"></i>
									<p>교통</p>
									<input type="text" id="traffic"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-umbrella-beach"></i>
									<p>문화</p>
									<input type="text" id="culture"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-tshirt"></i>
									<p>의류</p>
									<input type="text" id="clothing"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-won-sign"></i>
									<p>미용</p>
									<input type="text" id="beauty"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-mobile-alt"></i>
									<p>통신</p>
									<input type="text" id="telecom"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-wallet"></i>
									<p>회비</p>
									<input type="text" id="membership-fee"> 원
								</div>
								<div class="income-item">
									<i class="fas fa-cart-arrow-down"></i>
									<p>생필품</p>
									<input type="text" id="daily-necessity"> 원
								</div>
								<div class="income-item">
									<i class="far fa-envelope"></i>
									<p>경조사</p>
									<input type="text" id="occasions"> 원
								</div>
								<div class="income-items">
									<i class="fas fa-dollar-sign"></i>
									<p>총 합계</p>
									<p class="spending-total">원</p>
								</div>
							</div>
						</div>
						<div class="account-book-content">
							<h1>잔액</h1>
							<div class="total-income">
								<div class="income-item">
									<i class="fas fa-piggy-bank"></i>
									<p>총 수입</p>
									<p class="income-total">원</p>
								</div>
								<div class="income-item">
									<i class="fas fa-receipt"></i>
									<p>총 지출</p>
									<p class="spending-total">원</p>
								</div>
								<div class="income-items">
									<i class="fas fa-dollar-sign"></i>
									<p>총 합계</p>
									<p id="income-spending-total">원</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="swiper-rightslide" id="content-capsule">

					<a href="#" id="icon_timecapsule"><i
						class="fa-brands fa-creative-commons-sampling"></i></a>

					<div class="popup" id="popup_timecapsule">
						<div class="popup-content">
							<input type="date" class="date_timecapsule"> <input
								type="text" class="input_timecapsule" placeholder="비밀글을 작성해줘!"><br />
							<button class="save-btn" id="save_popup_timecapsule">저장</button>
							<button class="close-btn" id="close_popup_timecapsule">닫기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<footer id="footer">
		<span>© 2024 #오늘 일기</span>
		<div class="footer-main">
			<a href="/user/removeUser" class="account-deletion">&nbsp;회원탈퇴</a><a
				href="/user/logout" class="logout">로그아웃</a>
		</div>
	</footer>

	</div>




	<script type="text/javascript">
		// JSP EL을 사용하여 Java 변수 값을 자바스크립트 변수에 할당
		var loginUserId = '${user.userId}';
		console.log(loginUserId+"123456");
	</script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
		integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
		crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="/js/main.js"></script>
</body>

</html>
