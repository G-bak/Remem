$(document).ready(function() {

	console.log(loginUserId);

	






	// 기존 메뉴 버튼 클릭 이벤트
	$('.menu-btn').on('click', function() {
		console.log("버튼");
		$('.swiper-rightslide').hide();
		const target = $($(this).data('target'));
		// 버튼의 data-target이 #content-diary가 아닌 경우 세션 초기화
        if ($(this).data('target') !== '#content-diary') {
            sessionStorage.clear();
            console.log("세션 초기화");
        }
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



	


});














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









