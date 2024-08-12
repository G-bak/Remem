$(document).ready(function() {

	console.log(loginUserId);

	// 오늘 할일 전체 조회하는 ajax
	fetchTodoList();



	// 친구 추천 목록
	viewRecommendList();







	// 기존 메뉴 버튼 클릭 이벤트
	$('.menu-btn').on('click', function() {
		$('.swiper-rightslide').hide();
		const target = $($(this).data('target'));
		target.show();
	});

	// 팝업 설정 함수
	setupPopup('#profile-icon', '#profile-popup', '#close-profile-popup');
	setupPopup('#addfriend-icon', '#addfriend-popup', '#close-addfriend-popup');





	// 팝업의 닫기 버튼 클릭 시 팝업 닫기
	$('#popupfriendRequestCloseBtn').click(function() {
		$('#friendRequestPopup').hide();
	});



	// 날짜 입력값 변경 이벤트
	$('#input_date').on('change', function(event) {
		console.log('input_date change event');
		console.log(event.target.value);

		// Ajax 2024-08-20 data로 담아서 -> 서버 자료 요청
		// 서버 param 2024-08-20  -> DB select * from date -> 사용자 전달
		// Ajax 전달받은 값을 확인 -> 각 input 요소에 value로 세팅
	});

	// 저장 버튼을 눌렀을 때!
	$('#addTodoButton').on('click', addTodo);

	// 입력값을 Enter 키로 추가
	$('#todoInput').on('keypress', function(event) {
		if (event.key === 'Enter') {
			$('#addTodoButton').click(); // Enter 누르면 button 입력한 것과 동일하게 처리
		}
	});
});


//친구 추천 목록 조회 함수
function viewRecommendList() {
	$.ajax({
		url: "/viewRecommendList",
		type: "POST",
//		contentType: 'application/json; charset=utf-8',
		data: {
			loginUserId: loginUserId
		},
		dataType: "json",
		success: function(recommendList) {
			const recommendFriendListTable = $(".recommendfriend-list");
			recommendFriendListTable.empty();

			if (recommendList != null && recommendList != '') {
				//tr동적 
				recommendList.forEach((recommend) => {

					const row = `
					                <tr>
					                    <th>
					                        <div class="image"></div>
					                    </th>
					                    <td>${recommend.userId} ${recommend.userName}</td>
					                    <td><button id="${recommend.userId}" onclick=addFriend('${recommend.userId}')><i class="fas fa-user-plus"></i></button></td>
					                </tr>
					            `;
					recommendFriendListTable.append(row);
				});
			}


		},
		error: function() {
			alert("친구추천 에러 발생");
		}
	});
}



//친구 추천 목록 갱신
function refreshRecommendFriendList(){
	viewRecommendList();
}



//친구 신청함 버튼 클릭
$('#requestFriend').click(function() {
	$.ajax({
		url: "/confirmRequestFriend",
		type: "POST",
		//contentType: 'application/json; charset=utf-8',
		data: {
			loginUserId: loginUserId
		},
		success: function(response) {

			checkReceivedFriendRequests(response); //친구 신청 불러오는 함수

			$('#friendRequestPopup').show(); //친구신청 팝업 띄움

		},
		error: function() {
			alert("에러발생");
		}

	});

});

//친구 신청함 확인
function checkReceivedFriendRequests(response) {
	var friendRequestList = $('.friend-request-list'); //ul
	friendRequestList.empty();

	if (response != null && response != '') {
		response.forEach((requestFriend) => {
			let listItem = $('<li class="friend-request-item"></li>');
			listItem.append(`<div id="${requestFriend.userId}">${requestFriend.userId} ${requestFriend.userName}</div>`);
			listItem.append(`<button onclick="receiveFriendRequest('${requestFriend.userId}')">친구받기</button>`);


			friendRequestList.append(listItem);
		});

	}
}

//친구 받기 함수 호출
function receiveFriendRequest(friendId) {
	$.ajax({
		url: "/receiveFriendRequest",
		type: "POST",
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify({
			loginUserId: loginUserId,
			friendId: friendId
		}),
		success: function(response) {
			alert("친구받기완료!");
			checkReceivedFriendRequests(response); //친구신청함 비우고 다시 그리기
		},
		error: function() {
			alert("에러발생");
		}

	});
}



// 전체 할 일 조회 Ajax 요청
function fetchTodoList() {
	$.ajax({
		url: "/todoList/viewAll",
		type: "POST",
		data: { loginUserId: loginUserId },
		dataType: "json",
		success: function(response) {
			const todoList = $('#todoList');
			todoList.empty(); // 기존 리스트를 비움

			response.forEach(todo => {
				const li = $('<li>').attr('id', todo.todolistId);
				const checkbox = $('<input>', { type: 'checkbox' }).prop('checked', todo.todolistStatus === 'cmp');

				if (checkbox.prop('checked')) li.addClass('checked');

				checkbox.on('change', function() {
					handleCheckboxChange(this, li);
				});

				const textNode = document.createTextNode(todo.todolistContents);
				const removeButton = $('<button>').text('x').addClass('remove-btn').on('click', function() {
					if (confirm("삭제하시겠습니까?")) {
						removeTodoItem(li);
					}
				});

				li.append(checkbox, textNode, removeButton);
				todoList.append(li);
				$('#todoInput').val('');
			});
		},
		error: function() {
			alert("투두리스트 select 에러 발생");
		}
	});
}

// 체크박스 상태 변경 처리
function handleCheckboxChange(checkbox, li) {
	const status = $(checkbox).prop('checked') ? 'cmp' : 'reg';
	$.ajax({
		url: "/todoList/checkedOn",
		type: "POST",
		data: JSON.stringify({
			loginUserId: loginUserId,
			todoListId: li.attr('id'),
			todoListStatus: status
		}),
		contentType: 'application/json; charset=utf-8',
		success: function() {
			alert(`db 테이블 todoList_status 상태 ${status} 변경`);
			li.toggleClass('checked', $(checkbox).prop('checked'));
		},
		error: function() {
			alert("체크박스 상태 변경 에러 발생");
		}
	});
}

// 할 일 추가 처리
function addTodo() {
	const todoText = $('#todoInput').val().trim();
	if (todoText === '') {
		alert('할 일을 입력해주세용~');
		return;
	}
	//추가만하고 전체 조회하는 함수를 호출
	$.ajax({
		url: "/todoList/register",
		type: "POST",
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify({
			loginUserId: loginUserId,
			todoText: todoText
		}),
		success: function(response) {
			//			if (response > 0) {
			//				const li = $('<li>').attr('id', response);
			//				const checkbox = $('<input>', { type: 'checkbox' }).on('change', function() {
			//					handleCheckboxChange(this, li);
			//				});
			//
			//				const textNode = document.createTextNode(todoText);
			//				const removeButton = $('<button>').text('x').addClass('remove-btn').on('click', function() {
			//
			//					if (confirm("삭제하시겠습니까?")) {
			//						removeTodoItem(li);
			//					}
			//
			//				});
			//
			//				li.append(checkbox, textNode, removeButton);
			//				$('#todoList').append(li);
			//				$('#todoInput').val(''); // 저장 하려는 input clean 처리
			//			}
			fetchTodoList();
		},
		error: function() {
			alert("에러 발생");
		}
	});
}

// 할 일 삭제 처리
function removeTodoItem(li) {
	// TODO: 삭제 Ajax 요청 필요
	console.log(li.attr('id')); //li의 id 값 가져오는 방법

	$.ajax({
		url: "/todoList/remove",
		type: "POST",
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify({
			todoListId: li.attr('id'),
			loginUserId: loginUserId
		}),
		success: function(response) {
			//if (response > 0) {
			li.remove();
			//}
		},
		error: function() {
			alert("todoList 삭제 에러 발생");
		}

	});

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
});











// 친구 추가 팝업창 필터
function filterFriends() {
	const input = document.getElementById('name-input').value.toLowerCase(); // 입력된 값을 소문자로 변환하여 저장
	const table = document.getElementById('addfriend-list'); // 친구 목록 테이블을 가져옴


	if (input.length === 0) {
		$('#addfriend-list').empty(); // 입력이 없으면 테이블을 비움
		return;
	}

	$.ajax({
		url: "/searchFriend",
		type: "POST",
		data: JSON.stringify({
			loginUserId: loginUserId,
			userInput: input
		}), //사용자 아이디, 사용자 input
		contentType: 'application/json; charset=utf-8',
		success: function(response) {
			displayFriends(response);
			console.log(response);
		},
		error: function(error) {
			alert('에러:', error);
		}
	});


	//const rows = table.getElementsByTagName('tr'); // 테이블의 각 행(tr)을 가져옴

	//	for (let i = 0; i < rows.length; i++) { // 테이블의 각 행을 순회
	//		const td = rows[i].getElementsByTagName('td')[0]; // 각 행의 첫 번째 열(td)을 가져옴
	//		if (td) {
	//			const textValue = td.textContent || td.innerText; // 텍스트 값을 가져옴
	//			if (textValue.toLowerCase().indexOf(input) > -1) { // 텍스트 값에 입력된 값이 포함되는지 확인
	//				rows[i].style.display = ""; // 포함되면 행을 표시
	//			} else {
	//				rows[i].style.display = "none"; // 포함되지 않으면 행을 숨김
	//			}
	//		}
	//	}


}




function displayFriends(friends) {
	const table = $('#addfriend-list');
	table.empty(); // 기존의 목록을 비움

	friends.forEach(friend => {

		//console.log(friend);


		const addFriendButton =
			friend.friend ? `` : `<td><button id="${friend.userId}" onclick=addFriend('${friend.userId}')><i class="fas fa-user-plus"></i></button></td>`;

		const row = `
                <tr>
                    <th>
                        <div class="image"></div>
                    </th>
                    <td>${friend.userId}</td>
                    ${addFriendButton}
                </tr>
            `;
		table.append(row);
	});
}




function addFriend(friendId) {
	if (confirm(friendId + '님에게 친구신청을 하시겠습니까?')) {
		$.ajax({
			url: '/joinRequestFriend',
			type: "POST",
			data: JSON.stringify({
				loginUserId: loginUserId,
				friendId: friendId
			}),
			contentType: 'application/json; charset=utf-8',
			success: function(result) {
				if (result > 0) {
					alert(friendId + "님에게 친구신청 완료!");
					$(`button[id="${friendId}"]`).closest('td').remove();
				}
			},
			error: function(error) {
				alert('에러:', error);
			}
		});
	}
}



//혜민 파트 ****************************************************

/* 버튼 클릭 없이도 할일 조회 */








