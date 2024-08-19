<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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



<link href="/css/main.css" rel="stylesheet">
<title>마이페이지</title>
</head>

<body>
	<script>
window.onload = function() {
    // 세션 저장소에서 페이지 번호를 가져옵니다
    let pageNumber = sessionStorage.getItem("pageNumber");
    // 문자열을 숫자로 변환합니다
    let intPageNumber = Number(pageNumber);
    
    // 페이지 번호가 1 이상이면 content-diary를 표시합니다
    if (intPageNumber >= 1) {
    	document.getElementById("main-swiper-rightslide").style.display = 'none';
        document.getElementById("content-diary").style.display = 'block';
      
    }else {
       document.getElementById("content-diary").style.display = 'none';
    }
    
}
</script>
	<header id="header">
		<a href="/main"><span>#오늘 일기</span></a>
		<div class="icon-container">
			<a href="#" id="profile-icon"><i class="far fa-user-circle"></i></a>
			<a href="#" id="addfriend-icon"><i class="fas fa-user-plus"></i></a>
			<button id="requestFriend">
				<i class="fa-solid fa-user-group"></i>
			</button>
		</div>
	</header>


	<div id="friendRequestPopup" class="popupfriendRequest">
		<div class="popup-friendRequest-content">
			<div>
				<h1 style="font-size: 1.2rem; margin-bottom: 5px;">친구 신청 목록 📬</h1>
				<p style="color: gray;">친구신청을 받아줘~</p>
			</div>
			<ul class="friend-request-list">
				<!-- <li class="friend-request-item"><p>친구 신청 1</p></li>
            <li class="friend-request-item"><p>친구 신청 2</p></li> 
            <li class="friend-request-item"><p>친구 신청 3</p></li>  -->
			</ul>
			<button class="popup-friendRequest-close"
				id="popupfriendRequestCloseBtn">닫기</button>
		</div>
	</div>

	<div class="popup" id="profile-popup">
		<div class="popup-content">
			<h2>내 프로필</h2>
			<div class="profile-container">
				<!--                 <div class="photo"> -->
				<div class="profile"
					style="background-image: url('${sessionScope.filePath}');"></div>
				<form action="/upload" method="post" enctype="multipart/form-data"
					onsubmit="return validateForm()">

					<label for="fileInput" class="custom-file-button">파일 선택</label> <input
						type="file" name="file" id="fileInput"> <span
						id="fileName">파일을 선택하세요</span>

					<button type="submit"
						style="padding: 2px 9px; font-size: 0.9rem; position: absolute; right: 9%; top: 33%; cursor: pointer; border-radius: 5px; background-color: #c1e0ac;">올리기
					</button>
				</form>
				<!--             </div> -->


				<div>
					<p>팔로워</p>
					<p class="follower">${follower}</p>
				</div>
				<div>
					<p>팔로잉</p>
					<p class="following">${following}</p>
				</div>
			</div>
			<div class="profile-introduce">
				<p class="nickname">${user.userName}</p>
				<p class="profile-id">@${user.userId}</p>
				
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
						value="${user.userAddress}" readonly><br> <i
						class="fa-solid fa-magnifying-glass" id="search-icon"
						onclick="userAddressPostcode()"></i> </input><br> <input
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
					<input type="password" id="pw" name="currentPassword"
						placeholder="현재 비밀번호 입력"><br /> <input type="password"
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
			<h1 style="font-size: 1.3rem;">친구 검색 하기</h1>
			<p>친구의 아이디를 검색할 수 있어요!</p>

			<input type="text" id="name-input" placeholder="아이디 입력"
				style="border: 1px solid gray; border-radius: 8px;"
				oninput="filterFriends()">
			<button type="submit" id="search-btn">
				<i class="fa-solid fa-magnifying-glass" id="search-icon"></i>
			</button>
			<!--</form> -->
			<table class="addfriend-list" id="addfriend-list">
				<!--             js에서 친구목록 동적 생성 -->

			</table>
			<!--<button class="addfriend-close-btn" id="close-addfriend-popup">닫기</button> -->

			<div class="recommend-friend-header">
				<h1 style="font-size: 1.3rem;">친구 추천</h1>
				<button type="submit" id="recommend-search-btn"
					onclick="refreshRecommendFriendList()">

					<img id="refresh-image" src="/image/refresh-arrow.png" alt="친구목록갱신">
				</button>
			</div>

			<p>
				친구 추천 목록이에요!<br>새로고침 아이콘을 눌러서 추천 목록을 갱신할 수 있어요~
			</p>

			<!--<form onsubmit="return false;" id="frm-addfriend"> -->


			<!--             </form> -->
			<table class="recommendfriend-list" id="recommendfriend-list">
				<!--             동적으로 친구 추천 목록 생성 -->
			</table>
			<button class="addfriend-close-btn" id="close-addfriend-popup">닫기</button>
		</div>
	</div>

	<section class="section">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-leftslide">
					<button class="menu-btn" onclick="location.href='/main'">
						<img id="home-image" src="/image/home.png" alt="홈">
					</button>
					<button class="menu-btn" data-target="#content-diary">일기장</button>
					<button class="menu-btn" onclick="location.href='/calender'">캘린더</button>
					<button class="menu-btn" type="button" data-target="#content-todo"
						onclick="deleteSession()">오늘할일</button>
					<button class="menu-btn" data-target="#content-budget"
						onclick="deleteSession()">가계부</button>
					<button class="menu-btn" data-target="#content-capsule"
						onclick="deleteSession()">타임캡슐</button>

					<script>
               //function home(){
                 // document.getElementById("main-swiper-rightslide").style.display = 'none';
                  
              // }
               
               function deleteSession(){
                  sessionStorage.clear();
                     console.log("세션 초기화");
                  document.getElementById("content-diary").style.display = 'none';
                  document.getElementById("main-swiper-rightslide").style.display = 'none';
                  
               }
               </script>
				</div>

				<div id="main-swiper-rightslide">
					<div class="left-box">
						<h1 class="h1_post">#Time Line</h1>
						<div class="post_container">
							<!-- 일기 타임라인 영역 -->

							<!-- 일기 내용 영역 -->

							<!-- 일기 내용 영역 -->
						</div>

					</div>

					<div class="top-bottom-box">
						<div class="top-right-box">
							<div class="profile-info">
								<img src="${sessionScope.filePath}" class="profile-pics">
								<div class="profile-details">
									<p class="profile-name">${user.userName}</p>
									<p class="profile-id">@${user.userId}</p>
									<p class="last-active">접속 날짜: August 13, 2024</p>
								</div>
							</div>
						</div>


						<div class="bottom-right-box">
							<h3 class="friends-title">Friend List</h3>
							<div class="scroll_friends_list">
								<ul class="friends-list">
									<!-- 친구 정보 부분 -->

									<!-- 친구 정보 부분 -->
								</ul>
							</div>
						</div>

					</div>
				</div>

				<div class="swiper-rightslide" id="content-diary"
					style="display: ${currentPage >= 2 ? 'block' : 'none'};">
					<div class="diary-header">
						<a href="/diaryWrite">
							<button class="insert-btn">
								<i class="fa-regular fa-pen-to-square"></i>
							</button>
						</a>
						<button class="chatbot-btn" onclick="location.href='/chatjao'">
							<i class="fa-regular fa-comments"></i>
						</button>
					</div>

					<div class="diary-container">
						<div class="diary-entry-wrapper">
							<c:forEach var="diary" items="${userDiaryList}">
								<div class="diary-entry" data-diary-id="${diary.diaryId}">
									<h3>${diary.diaryTitle}</h3>
									<span class="diary-date">${diary.writeDate}</span>
									<div class="diary-footer">
										<button class="diary-container-view-btn" id="view-diary">확인</button>
										<button class="diary-container-modify-btn" id="modify-diary">수정</button>
										<button class="diary-container-remove-btn" id="remove-diary"
											data-diary-id="${diary.diaryId}">삭제</button>
									</div>
									<p class="diary-content" style="display: none;">${diary.diaryContent}</p>
								</div>
							</c:forEach>
						</div>

						<div class="pagination-container">
							<c:if test="${currentPage > 1}">
								<a href="/main?page=${currentPage - 1}" class="prev-button">
									이전 </a>
							</c:if>

							<c:forEach var="i" begin="1" end="${totalPages}">
								<a href="/main?page=${i}"
									class="number-button ${i == currentPage ? 'active' : ''}"
									onclick="saveSessionPage(${i})">${i}</a>
								<script>
               function saveSessionPage(pageNumber) {
                   // 세션 저장소에 페이지 번호 저장
                   sessionStorage.setItem('pageNumber', pageNumber);
               }
            </script>
							</c:forEach>

							<c:if test="${currentPage < totalPages}">
								<a href="/main?page=${currentPage + 1}" class="next-button">
									이후 </a>
							</c:if>
						</div>
					</div>

					<div class="diary-view-popup" id="diary-view-popup">
						<div class="diary-view-content">
							<h1 style="font-size: 1.5rem; margin-left: 15px;">일기 확인하기📒</h1>

							<div id="frm-view-diary">


								<label class="title-label"><input type="text"
									id="diary-title-view" name="diaryTitle" readonly></label> <label
									class="date-label"><input type="text"
									id="diary-date-view" name="writeDate" readonly></label>
								<textarea rows="5" cols="45" id="diary-content-view"
									name="diaryContent" readonly></textarea>
								<button class="diary-view-close-btn" id="close-view-diary-popup">닫기</button>

							</div>
						</div>
					</div>

					<div class="diary-modify-popup" id="diary-modify-popup">
						<div class="diary-modify-content">
							<h1 style="font-size: 1.5rem; margin-left: 10px;">일기 수정하기✏️</h1>

							<form action="/modifyDiary" method="post" id="frm-modify-diary">
								<input type="hidden" id="diaryId" name="diaryId"> <label
									class="title-label"><input type="text"
									id="diary-title-modify" name="diaryTitle"></label> <label
									class="date-label"><input type="text"
									id="diary-date-modify" name="writeDate" readonly> </label>

								<textarea rows="5" cols="45" id="diary-content-modify"
									name="diaryContent"></textarea>
								<div class="btns-diary-modify">
									<button type="submit" class="diary-modify-btn"
										id="modify-diary-popup">수정</button>

									<button class="diary-modify-close-btn"
										id="close-modify-diary-popup">닫기</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="swiper-rightslide" id="content-todo">
					<div class="wrapper">
						<header>#Check List</header>
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
							<div class="total-spending">
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
							<div class="total-income-spending">
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
				<div class="swiper-rightslide" id="content-capsule"
					style="display: none;">
					<div class="div_a_icon">
						<a href="#" id="icon_timecapsule"> <i
							class="fa-brands fa-creative-commons-sampling"></i>
						</a>
						<div class="popup" id="popup_timecapsule">
							<div class="popup-content">
								<input type="date" id="date_timecapsule"
									class="date_timecapsule">


								<textarea id="input_timecapsule" class="input_timecapsule"
									rows="10" cols="50" placeholder="타임캡슐을 만들어줘!"></textarea>
								<br />
								<button class="save-btn" id="save_popup_timecapsule">저장</button>
								<button class="close-btn" id="close_popup_timecapsule">닫기</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<footer id="footer">
		<span>© 2024 #오늘 일기</span>
		<div class="footer-main">
			<a href="/user/removeUser" class="account-deletion"
				onclick="return confirmDeletion();">&nbsp;회원탈퇴</a> <a
				href="/user/logout" class="logout">로그아웃</a>
		</div>
	</footer>

	<script>
      // JSP 내에서 사용자가 제출한 폼에 대해 발생한 오류 메시지를 알림으로 보여줌
      <c:if test="${not empty errorMessage}">
      alert("${errorMessage}");
      </c:if>
   </script>

	<script type="text/javascript">
      // JSP EL을 사용하여 Java 변수 값을 자바스크립트 변수에 할당
      var loginUserId = '${user.userId}';
      console.log(loginUserId);

      var filePath = '${sessionScope.filePath}';
      console.log(filePath + "123");
   </script>
   
   
   
   
   <script type="text/javascript">
	// 주소 변경
   function userAddressPostcode() {
           new daum.Postcode({
               oncomplete: function(data) {
                   // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                   // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                   // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                   var addr = ''; // 주소 변수
                   var extraAddr = ''; // 참고항목 변수

                   //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                   if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                       addr = data.roadAddress;
                   } else { // 사용자가 지번 주소를 선택했을 경우(J)
                       addr = data.jibunAddress;
                   }

                   // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                   if(data.userSelectedType === 'R'){
                       // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                       // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                       if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                           extraAddr += data.bname;
                       }
                       // 건물명이 있고, 공동주택일 경우 추가한다.
                       if(data.buildingName !== '' && data.apartment === 'Y'){
                           extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                       }
                       // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                       if(extraAddr !== ''){
                           extraAddr = ' (' + extraAddr + ')';
                       }
                       // 조합된 참고항목을 해당 필드에 넣는다.
                       document.getElementById("sample6_extraAddress").value = extraAddr;

                   } else {
                       document.getElementById("sample6_extraAddress").value = '';
                   }

                   // 우편번호와 주소 정보를 해당 필드에 넣는다.
                   document.getElementById('sample6_postcode').value = data.zonecode;
                   document.getElementById("sample6_address").value = addr;
                   // 커서를 상세주소 필드로 이동한다.
                   document.getElementById("sample6_detailAddress").focus();
               }
           }).open();
       }
   </script>
   
   
   
   
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
		integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
		crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="/js/accountBook.js"></script>
	<script src="/js/followFriendAndTodoList.js"></script>
	<script src="/js/userManage.js"></script>
</body>

</html>
