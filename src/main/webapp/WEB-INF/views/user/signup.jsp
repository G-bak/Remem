<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<link rel="stylesheet"
	href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
<link href="/css/signup.css" rel="stylesheet">
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<title>Insert title here</title>
</head>
<body class="signup-body">
	<!-- <img src="/image/grass.png" alt="Background Image"> -->
	<h1>#오늘 일기</h1>
	<h2>회원가입</h2>
	<form action="/user/signup" method="post" id="frm_signup">
		<input type="text" id="userName" name="userName"
			placeholder="너의 이름이 뭐야?" required><br />
		<div class="input-with-button">
			<input type="text" id="userId" name="userId" placeholder="아이디를 입력해줘!"
				required><br />
			<button type="button" id="btn-checkduplicated">중복체크</button>
		</div>
		<!-- 아이디 중복체크 -->
		<p id="p-duplicatedText"></p>
		<input type="hidden" id="duplicated-check-result">
		<input type="password" id="userPassword" name="userPassword" placeholder="비밀번호를 입력해줘!" required>
		<br />
		<input type="hidden" id="sample6_postcode" placeholder="우편번호">
		
		<div class="address-box">
			<input type="text" id="sample6_address" name="userAddress" placeholder="주소"><br>
			<input type="button" id="btn-addressSearch" onclick="sample6_execDaumPostcode()">
		</div>
		
		
		
		<br>
		<input type="hidden" id="sample6_detailAddress" placeholder="상세주소">
		<br>
		<input type="hidden" id="sample6_extraAddress" placeholder="참고항목">
		
		<button type="submit">
			회원가입<i class="fab fa-gratipay"></i>
		</button>
	</form>

	<script>
	
	
	// JSP 내에서 사용자가 제출한 폼에 대해 발생한 오류 메시지를 알림으로 보여줌
	 <c:if test="${not empty errorMessage}">
	     alert("${errorMessage}");
	 </c:if>
	
	const frm_signup =  document.querySelector('#frm_signup');

	frm_signup.addEventListener('submit', (e) => {
		e.preventDefault();

		let userName = document.getElementById('userName').value.trim();
		let userId = document.getElementById('userId').value.trim();
		let userPassword = document.getElementById('userPassword').value.trim();
		let sample6_address = document.getElementById('sample6_address').value.trim();
		
		//아이디 중복체크
		let checkDuplicated = $('#duplicated-check-result').val().trim();
		
		if(userName === '' || userName === null){
			alert('Name은 필수 입력입니다.');
			document.getElementById('userName').focus();
			return false;
		}
		
		if(userId === '' || userId === null){
			alert('Id는 필수 입력입니다.');
			document.getElementById('userId').focus();
			return false;
		}
		
		if(userPassword === '' || userPassword === null){
			alert('Password는 필수 입력입니다.');
			document.getElementById('userPassword').focus();
			return false;
		}
		
		if(sample6_address === '' || sample6_address === null){
			alert('Address는 필수 입력입니다.');
			document.getElementById('sample6_address').focus();
			return false;
		}
		
		if(checkDuplicated === '' || checkDuplicated === null){
			//js에서 input hidden에 value넣는 처리하고 여기서 값있는지 없는지 확인
			alert('아이디 중복체크는 필수입니다.');
			document.getElementById('btn-checkduplicated').focus();
			return false;
		}
		
		frm_signup.submit();
	});
	
	 function sample6_execDaumPostcode() {
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
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="/js/userManage.js"></script>
</body>
</html>
