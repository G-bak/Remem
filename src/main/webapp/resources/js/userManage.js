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

	//일기추가 팝업창
	/*$('.insert-btn').on('click', function(event) {
		event.preventDefault();
		$('#frm-diary')[0].reset();
		$('#save-diary-popup').show();
		currentDiaryEntry = null;
		$('#diary-popup').fadeIn();
	});

	$('#close-diary-popup').on('click', function(event) {
		event.preventDefault();
		$('#diary-popup').fadeOut();
	});

	$('#diary-popup').on('click', function(event) {
		if ($(event.target).is('#diary-popup')) {
			$('#diary-popup').fadeOut();
		}
	});*/

	// 일기 추가 및 수정
	$('#save-diary-popup').on('click', function(event) {
		event.preventDefault();

		const date = $('#diary-date').val();
		const title = $('#diary-title').val();
		const content = $('#diary-content').val();

		if (date && title && content) {
			if (currentDiaryEntry) {
				currentDiaryEntry.find('h3').text(title);
				currentDiaryEntry.find('.diary-date').text(date);
				currentDiaryEntry.find('.diary-content').text(content);
			} else {
				const diaryEntry = `
                            <div class="diary-entry">
                                <h3>${title}</h3>
                                <span class="diary-date">${date}</span>
                                <div class="diary-footer">
                                    <button class="diary-view-btn" id="view-diary">확인</button>
                                    <button class="diary-modify-btn" id="modify-diary">수정</button>
                                    <button class="diary-remove-btn" id="remove-diary">삭제</button>
                                </div>
                                <p class="diary-content" style="display:none;">${content}</p>
                            </div>
                        `;
				$('.diary-container').append(diaryEntry);
			}

			$('#frm-diary')[0].reset();
			currentDiaryEntry = null;
			$('#diary-popup').fadeOut();
		} else {
			alert('모든 필드를 작성해 주세요.');
		}
	});

	// 일기 수정
	$(document).on('click', '.diary-modify-btn', function() {
		currentDiaryEntry = $(this).closest('.diary-entry');
		const title = currentDiaryEntry.find('h3').text();
		const date = currentDiaryEntry.find('.diary-date').text();
		const content = currentDiaryEntry.find('.diary-content').text();

		$('#diary-date').val(date);
		$('#diary-title').val(title);
		$('#diary-content').val(content);
		$('#save-diary-popup').show();

		$('#diary-popup').fadeIn();
	});

	// 일기 확인
	$(document).on('click', '.diary-view-btn', function() {
		currentDiaryEntry = $(this).closest('.diary-entry');
		const title = currentDiaryEntry.find('h3').text();
		const date = currentDiaryEntry.find('.diary-date').text();
		const content = currentDiaryEntry.find('.diary-content').text();

		$('#diary-date').val(date);
		$('#diary-title').val(title);
		$('#diary-content').val(content);
		$('#save-diary-popup').hide();

		$('#diary-popup').fadeIn();
	});

	// 일기 삭제
	$(document).on('click', '.diary-remove-btn', function() {
		if (confirm('정말로 삭제하시겠습니까?')) {
			$(this).closest('.diary-entry').remove();
		}
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




