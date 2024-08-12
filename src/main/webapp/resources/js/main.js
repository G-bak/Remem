$(document).ready(function() {
    // 기존 메뉴 버튼 클릭 이벤트
    $('.menu-btn').on('click', function() {
        $('.swiper-rightslide').hide();
        $($(this).data('target')).show();
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

    // 기존 팝업 설정
    setupPopup('#profile-icon', '#profile-popup', '#close-profile-popup');
    setupPopup('#addfriend-icon', '#addfriend-popup', '#close-addfriend-popup');
    setupPopup('#icon_timecapsule', '#popup_timecapsule', '#close_popup_timecapsule');
});


$(document).ready(function() {
    $('#input-date').on('change', function(event) {
        console.log('input_date change event');
        console.log(event.target.value);

        $.ajax({
            url: "/view/AccountBook",
            type: "POST",
            data: JSON.stringify({
                userId: 'user2',
                accountDate: $('#input-date').val()
            }),
            contentType: "application/json; charset=utf-8",
            success: function(ab) {
                console.log(ab+"123131");
                const accountContainer = $('.accountModify-popup-content');

                // 기존 버튼 제거
                accountContainer.find('button').remove();

                if (ab == null || ab =='') {
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

    function saveOrUpdateAccountBook(url, callback) {
        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify({
                userId: 'user2',
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
});

//정민 파트
$(document).ready(function() {
	let currentDiaryEntry = null;

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

	$('.insert-btn').on('click', function(event) {
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
	});

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
});

// 친구 추가 팝업창 필터
function filterFriends() {
	const input = document.getElementById('name-input').value.toLowerCase();
	const table = document.getElementById('addfriend-list');
	const rows = table.getElementsByTagName('tr');

	for (let i = 0; i < rows.length; i++) {
		const td = rows[i].getElementsByTagName('td')[0];
		if (td) {
			const textValue = td.textContent || td.innerText;
			if (textValue.toLowerCase().indexOf(input) > -1) {
				rows[i].style.display = "";
			} else {
				rows[i].style.display = "none";
			}
		}
	}
}

//혜민 파트

document.addEventListener('DOMContentLoaded', () => {
	const addButton = document.getElementById('addTodoButton');
	const inputField = document.getElementById('todoInput');
	const todoList = document.getElementById('todoList');

	addButton.addEventListener('click', () => {
		const todoText = inputField.value.trim();

		if (todoText === '') {
			alert('할 일을 입력해주세용~');
			return;
		}

		const li = document.createElement('li');

		// Create checkbox
		const checkbox = document.createElement('input');
		checkbox.type = 'checkbox';
		checkbox.addEventListener('change', () => {
			if (checkbox.checked) {
				li.classList.add('checked');
			} else {
				li.classList.remove('checked');
			}
		});

		// Create text node
		const textNode = document.createTextNode(todoText);

		// Create remove button
		const removeButton = document.createElement('button');
		removeButton.textContent = 'x';
		removeButton.classList.add('remove-btn');
		removeButton.addEventListener('click', () => {
			todoList.removeChild(li);
		});

		li.appendChild(checkbox);
		li.appendChild(textNode);
		li.appendChild(removeButton);
		todoList.appendChild(li);

		inputField.value = '';
	});

	inputField.addEventListener('keypress', (event) => {
		if (event.key === 'Enter') {
			addButton.click();
		}
	});
});

