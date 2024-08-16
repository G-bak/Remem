$(document).ready(function() {

	console.log(loginUserId);



	// 기존 메뉴 버튼 클릭 이벤트
	$('.menu-btn').on('click', function() {

		$('#main-swiper-rightslide').hide();
		$('.swiper-rightslide').hide();
		const target = $($(this).data('target'));
		target.show();
	});

	//	// 팝업 설정 함수
	//	setupPopup('#profile-icon', '#profile-popup', '#close-profile-popup');
	//	setupPopup('#addfriend-icon', '#addfriend-popup', '#close-addfriend-popup');

	// 기존 팝업 설정
	setupPopup('#profile-icon', '#profile-popup', '#close-profile-popup');
	setupPopup('#addfriend-icon', '#addfriend-popup', '#close-addfriend-popup');
	setupPopup('#icon_timecapsule', '#popup_timecapsule', '#close_popup_timecapsule');



	// 팝업의 닫기 버튼 클릭 시 팝업 닫기
	$('#popupfriendRequestCloseBtn').click(function() {
		$('#friendRequestPopup').hide();
	});




	loadAllTimecapsules();

	function loadAllTimecapsules() {
		$.ajax({
			url: '/all/Timecapsules',
			type: 'GET',
			success: function(response) {
				response.forEach(function(tc) {
					addTimecapsule(tc.tcDate, tc.tcContent);
				});
			},
			error: function(error) {
				console.error('타임캡슐을 불러오는 중 오류가 발생했습니다.', error);
			}
		});
	}

	// 타임캡슐 추가 (*******************************)
	function addTimecapsule(date, content) {
		const additionalContent = document.createElement('div');
		additionalContent.className = "additional-content";
		additionalContent.innerHTML = `
	        <div class="photo_additional"></div>
	        <p class="additional_date"></p>
	        <button class="open_additonal" data-content="${content}" data-date="${date}">열기</button>
	    `;

		$('#content-capsule').append(additionalContent);

		// 개별 타임캡슐에 대해 타이머 시작
		additionalContent.intervalId = setInterval(function() {
			updateTimer(date, additionalContent);
		}, 1000);
	}













	// 타임캡슐 저장 버튼
	$('#save_popup_timecapsule').on('click', function(event) {
		event.preventDefault();


		//입력할 값
		var tcDate = $('#date_timecapsule').val();
		var tcContent = $('#input_timecapsule').val();

		var today = new Date();
		var year = today.getFullYear();
		var month = String(today.getMonth() + 1).padStart(2, '0');  // 월은 0부터 시작하므로 +1
		var day = String(today.getDate()).padStart(2, '0');
		var formattedToday = year + '-' + month + '-' + day;

		// tcDate가 오늘 날짜 이전인지 비교
		if (tcDate < formattedToday) {
			alert("타임캡슐은 오늘날짜 이전으로 지정 못해 ! 날짜 다시 선택해줘");
			return;
		}








		$.ajax({
			url: '/save/Timecapsule',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				tcUserId: 'user1',
				tcDate: tcDate,
				tcContent: tcContent
			}),
			success: function(response) {
				if (response) {
					alert('타임캡슐이 성공적으로 저장되었습니다!');
					addTimecapsule(response.tcDate, response.tcContent);
				} else {
					alert('저장에 실패했습니다.');
				}
			},
			error: function(error) {
				console.error('저장 중 오류가 발생했습니다.', error);
			}
		});
	});

	// 열기 버튼 클릭 -> 타임캡슐 내용
	$(document).on('click', '.open_additonal', function() {
		var content = $(this).data('content');
		var date = $(this).data('date');  // 버튼에 저장된 날짜 가져오기

		const currentDate = new Date();
		const formattedDate = currentDate.toISOString().split('T')[0];

		if (date <= formattedDate) {
			var popupHtml =
				`<div class="popup_html" id="popup_open_timecapsule">
                <div class="popup_open_content">
            	   <h1>비밀글을 읽을 수 있어요!</h1>
                   <p id="popup-message">${content}</p>
	               <p class="popup_open_close">닫기</p>
                </div>
            </div>`;

			$('body').append(popupHtml);

			$('#popup_open_timecapsule').fadeIn();

			$(document).on('click', '.popup_open_close', function() {
				$('#popup_open_timecapsule').fadeOut(function() {
					$(this).remove();
				});
			});

			$(document).on('click', '#popup_open_timecapsule', function(e) {
				if ($(e.target).is('#popup_open_timecapsule')) {
					$('#popup_open_timecapsule').fadeOut(function() {
						$(this).remove();
					});
				}
			});
		} else {
			alert("지정된 날짜 이후에만 타임캡슐을 열 수 있습니다!");
		}
	});
});






function updateTimer(date, targetElement) {
	const future = Date.parse(date);
	const now = new Date();
	const diff = future - now;

	if (diff <= 0) {
		clearInterval(targetElement.intervalId); // 타이머 멈추기
		targetElement.querySelector(".additional_date").innerText = "타임캡슐을 열어줘!";
		targetElement.querySelector('.photo_additional').style.backgroundImage = "url('/image/time-capsule-clear-icon.png')";
		return;
	}

	const days = Math.floor(diff / (1000 * 60 * 60 * 24));
	const hours = Math.floor((diff / (1000 * 60 * 60)) % 24);
	const mins = Math.floor((diff / (1000 * 60)) % 60);
	const secs = Math.floor((diff / 1000) % 60);

	targetElement.querySelector(".additional_date").innerText =
		`${days}일 ${hours}시간 ${mins}분 ${secs}초`;
}























// 팝업 설정 함수
function setupPopup(triggerId, popupId, closeId) {
	$(triggerId).on('click', function(event) {
		event.preventDefault();
		$(popupId).fadeIn();
	});

	$(closeId).on('click', function() {
		$(popupId).fadeOut();
	});

	$(popupId).on('click', function(event) {
		if ($(event.target).is(popupId)) {
			$(popupId).fadeOut();
		}
	});
}





$(document).ready(function() {
	$('#input-date').on('change', function(event) {
		console.log('input_date change event');
		console.log(event.target.value);

		$.ajax({
			url: "/view/AccountBook",
			type: "POST",
			data: JSON.stringify({
				userId: loginUserId,
				accountDate: $('#input-date').val()
			}),
			contentType: "application/json; charset=utf-8",
			success: function(ab) {
				console.log(ab + "123131");
				const accountContainer = $('.accountModify-popup-content');

				// 기존 버튼 제거
				accountContainer.find('button').remove();

				if (ab == null || ab == '') {
					accountContainer.append('<button id="btn_account_save">저장</button>');
					registerSaveButtonHandler(); // 저장 버튼 핸들러 등록
				} else {
					accountContainer.append('<button id="btn_account_modify">수정</button>');
					registerModifyButtonHandler(); // 수정 버튼 핸들러 등록
				}

				const accountbook = ab || {};
				$('#salary').val(accountbook.salary || '');
				$('#side-job').val(accountbook.sideJob || '');
				$('#saving').val(accountbook.saving || '');
				$('.income-total').text((accountbook.incomeTotal || 0) + ' 원');

				$('#food-expenses').val(accountbook.foodExpenses || '');
				$('#traffic').val(accountbook.traffic || '');
				$('#culture').val(accountbook.culture || '');
				$('#clothing').val(accountbook.clothing || '');
				$('#beauty').val(accountbook.beauty || '');
				$('#telecom').val(accountbook.telecom || '');
				$('#membership-fee').val(accountbook.membershipFee || '');
				$('#daily-necessity').val(accountbook.dailyNecessity || '');
				$('#occasions').val(accountbook.occasions || '');
				$('.spending-total').text((accountbook.spendingTotal || 0) + ' 원');
				$('#income-spending-total').text((accountbook.incomeSpendingTotal || 0) + ' 원');
			},
			error: function() {
				alert('잘못됐어! 다시해줘');
			}
		});
	});


});







function saveOrUpdateAccountBook(url, callback) {
	$.ajax({
		url: url,
		type: "POST",
		data: JSON.stringify({
			userId: loginUserId,
			accountDate: $('#input-date').val(),
			salary: parseFloat($('#salary').val()) || 0,
			sideJob: parseFloat($('#side-job').val()) || 0,
			saving: parseFloat($('#saving').val()) || 0,
			incomeTotal: parseFloat($('.income-total').text()) || 0,
			foodExpenses: parseFloat($('#food-expenses').val()) || 0,
			traffic: parseFloat($('#traffic').val()) || 0,
			culture: parseFloat($('#culture').val()) || 0,
			clothing: parseFloat($('#clothing').val()) || 0,
			beauty: parseFloat($('#beauty').val()) || 0,
			telecom: parseFloat($('#telecom').val()) || 0,
			membershipFee: parseFloat($('#membership-fee').val()) || 0,
			dailyNecessity: parseFloat($('#daily-necessity').val()) || 0,
			occasions: parseFloat($('#occasions').val()) || 0,
			spendingTotal: parseFloat($('.spending-total').text()) || 0,
			incomeSpendingTotal: parseFloat($('#income-spending-total').text()) || 0
		}),
		contentType: "application/json; charset=utf-8",
		success: function(savedAccountBook) {
			console.log("Server response:", savedAccountBook);

			const abs = savedAccountBook || {};
			const incomeTotal = abs.salary + abs.sideJob + abs.saving;
			const spendingTotal = abs.foodExpenses + abs.traffic + abs.culture + abs.clothing + abs.beauty +
				abs.telecom + abs.membershipFee + abs.dailyNecessity + abs.occasions;
			const incomeSpendingTotal = incomeTotal - spendingTotal;

			$('#salary').val(abs.salary || '');
			$('#side-job').val(abs.sideJob || '');
			$('#saving').val(abs.saving || '');
			$('.income-total').text((incomeTotal || 0) + ' 원');

			$('#food-expenses').val(abs.foodExpenses || '');
			$('#traffic').val(abs.traffic || '');
			$('#culture').val(abs.culture || '');
			$('#clothing').val(abs.clothing || '');
			$('#beauty').val(abs.beauty || '');
			$('#telecom').val(abs.telecom || '');
			$('#membership-fee').val(abs.membershipFee || '');
			$('#daily-necessity').val(abs.dailyNecessity || '');
			$('#occasions').val(abs.occasions || '');
			$('.spending-total').text((spendingTotal || 0) + ' 원');
			$('#income-spending-total').text((incomeSpendingTotal || 0) + ' 원');

			alert("저장완료");

			// 콜백 함수 호출 (예: 저장 후 수정 버튼으로 변경)
			if (typeof callback === 'function') {
				callback();
			}
		},
		error: function(xhr, status, error) {
			console.error("AJAX Error:");
			console.error("Status:", status);
			console.error("Error:", error);
			console.error("Response:", xhr.responseText);
			alert('저장에 실패했습니다. 다시 시도해주세요.');
		}
	});
}

function registerSaveButtonHandler() {
	$('#btn_account_save').off('click').on('click', function(event) {
		console.log('btn_account_save click');
		saveOrUpdateAccountBook("/save/AccountBook", function() {
			// 저장 후에는 수정 버튼으로 변경
			$('#btn_account_save').remove();
			$('.accountModify-popup-content').append('<button id="btn_account_modify">수정</button>');
			registerModifyButtonHandler();
		});
	});
}

function registerModifyButtonHandler() {
	$('#btn_account_modify').off('click').on('click', function(event) {
		console.log('btn_account_modify click');
		saveOrUpdateAccountBook("/modify/AccountBook");
	});
}









