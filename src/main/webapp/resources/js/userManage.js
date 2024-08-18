//정민 파트
$(document).ready(function() {
	let currentDiaryEntry = null;

	//프로필 팝업창
	$('.menu-btn').on('click', function() {
		
		$('.swiper-rightslide').hide();

		$($(this).data('target')).show();
	});

	$('#profile-icon').on('click', function(event) {
		event.preventDefault();
		$('#profile-popup').fadeIn();
	});

	$('#close-profile-popup').on('click', function() {
		$('#profile-popup').fadeOut();
	});

	$('#profile-popup').on('click', function(event) {
		if ($(event.target).is('#profile-popup')) {
			$('#profile-popup').fadeOut();
		}
	});

	//주소 팝업창
	$('#open-address-popup').on('click', function(event) {
		event.preventDefault();
		$('#address-popup').fadeIn();
	});

	$('#close-address-popup').on('click', function() {
		$('#address-popup').fadeOut();
	});

	$('#address-popup').on('click', function(event) {
		if ($(event.target).is('#address-popup')) {
			$('#address-popup').fadeOut();
		}
	});

	//비밀번호 팝업창
	$('#open-password-popup').on('click', function(event) {
		event.preventDefault();
		$('#password-popup').fadeIn();
	});

	$('#close-password-popup').on('click', function() {
		$('#password-popup').fadeOut();
	});

	$('#password-popup').on('click', function(event) {
		if ($(event.target).is('#password-popup')) {
			$('#password-popup').fadeOut();
		}
	});

	//친구추가 팝업창
	$('#addfriend-icon').on('click', function(event) {
		event.preventDefault();
		$('#addfriend-popup').fadeIn();
	});

	$('#close-addfriend-popup').on('click', function() {
		$('#addfriend-popup').fadeOut();
	});

	$('#addfriend-popup').on('click', function(event) {
		if ($(event.target).is('#addfriend-popup')) {
			$('#addfriend-popup').fadeOut();
		}
	});

	// 일기 확인 팝업창
	$(document).on('click', '.diary-container-view-btn', function(event) {
		event.preventDefault();

		// 현재 클릭한 일기 항목을 가져옴
		const currentDiaryEntry = $(this).closest('.diary-entry');
		const title = currentDiaryEntry.find('h3').text();
		const date = currentDiaryEntry.find('.diary-date').text();
		const content = currentDiaryEntry.find('.diary-content').text();

		// 팝업에 일기 내용을 설정
		$('#diary-date-view').val(date);
		$('#diary-title-view').val(title);
		$('#diary-content-view').val(content);

		// 팝업을 띄움
		$('#diary-view-popup').fadeIn();
	});

	$('#close-view-diary-popup').on('click', function() {
		$('#diary-view-popup').fadeOut();
	});

	$('#diary-view-popup').on('click', function(event) {
		if ($(event.target).is('#diary-view-popup')) {
			$('#diary-view-popup').fadeOut();
		}
	});

	// 일기 수정 팝업창
	$(document).on('click', '.diary-container-modify-btn', function(event) {
		event.preventDefault();

	 // 현재 클릭한 일기 항목을 가져옴
		const currentDiaryEntry = $(this).closest('.diary-entry');
		const diaryId = currentDiaryEntry.data('diary-id'); // 일기 ID 가져오기
		const title = currentDiaryEntry.find('h3').text();
		const date = currentDiaryEntry.find('.diary-date').text();
		const content = currentDiaryEntry.find('.diary-content').text();

		// 팝업에 일기 내용을 설정
		$('#diaryId').val(diaryId); // 숨겨진 input에 diary ID 설정
		$('#diary-date-modify').val(date);
		$('#diary-title-modify').val(title);
		$('#diary-content-modify').val(content);
	
		// 팝업을 띄움
		$('#diary-modify-popup').fadeIn();
	});
	
	// 팝업창 닫기 버튼 클릭 시 팝업창 닫기
	$('#close-modify-diary-popup').on('click', function(event) {
		event.preventDefault();
		$('#diary-modify-popup').fadeOut();
	});
	
	// 팝업창 외부 클릭 시 팝업창 닫기
	$('#diary-modify-popup').on('click', function(event) {
		if ($(event.target).is('#diary-modify-popup')) {
			$('#diary-modify-popup').fadeOut();
		}
	});
	
	//수정 버튼 클릭시 폼 제출
	$('#modify-diary-popup').on('click', function() {
    	$('#frm-modify-diary').submit();
	});
	
	// 폼 제출 시 팝업창 닫기 및 페이지 새로고침
	$('#frm-modify-diary').on('submit', function() {
	    $('#diary-modify-popup').fadeOut();  // 팝업창 닫기
	});
	
	//회원 삭제
	 $('.diary-container-remove-btn').on('click', function(event) {
        event.preventDefault(); // 기본 동작 방지

        var diaryId = $(this).data('diary-id'); // 클릭한 버튼의 diaryId 가져오기

        // confirm 창 띄우기
        if (confirm("정말로 이 일기를 삭제하시겠습니까?")) {
            // 확인을 누르면 삭제 요청 수행
            window.location.href = '/deleteDiary?diaryId=' + diaryId;
        }
    });
	
	
	//회원 탈퇴
	$(document).ready(function() {
	    $('.account-deletion').on('click', function(event) {
	        // confirm 창 띄우기
	        if (!confirm("정말로 회원탈퇴를 하시겠습니까?")) {
	            // 사용자가 취소를 누르면 기본 동작(페이지 이동)을 막음
	            event.preventDefault();
	        }
	    });
	});
	








	$('#btn-checkduplicated').on('click', function() {
		let inputId = $('#userId').val().trim();

		if (!inputId) {
			alert('아이디를 입력해 주세요!');
			return;
		}

		$.ajax({
			url: 'http://localhost:8080/checkIdDuplicated',
			type: "POST",
			contentType: 'application/json; charset=utf-8',
			//dataType: 'json', // 응답 형식을 JSON으로 설정
			data: JSON.stringify({
				signupId: inputId
			}),
			success: function(response) {
				console.log(response);
				
				if (response  === 1) {
					$('#duplicated-check-result').val(null);
					$('#p-duplicatedText').text("아이디가 중복됐어~ 다시 입력해줘");
				}  else {
					//value 값 넣어주기
					$('#duplicated-check-result').val("중복확인완료");
					$('#p-duplicatedText').text("중복확인 됐어! 회원가입 진행해줘~");
				}
			},
			error: function() {
				
				alert("아이디 중복체크 서버 에러");
			}
		});
	});










});




