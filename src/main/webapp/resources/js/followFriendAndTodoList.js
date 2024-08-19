$(document).ready(function() {

	// 오늘 할일 전체 조회하는 ajax
	fetchTodoList();

	// 친구 추천 목록
	viewRecommendList();

	//mainDashBoard에서 친구들 일기 타임라인 조회
	getFriendsDiaryTimeline();

	//mainDashBoard에서 친구 목록 조회
	getFriendList();


	// 로그인 사용자 프로필 사진 업로드 시 파일명 처리
	$("#fileInput").on("change", function() {
		var fileName = $(this).val().split('\\').pop();
		$("#fileName").text(fileName);
	});


	//로그인한 사용자 프로필 사진 처리
	if (filePath == null || filePath == '') {
		//등록된 프로필 사진이 없다면 기본 이미지로 변경
		$('.profile-pics').attr('src', "/uploads/basic_profile.jpg");
		$('.profile').css('background-image', 'url(/uploads/basic_profile.jpg)');
	}


	// 현재 날짜 가져오기
	const now = new Date();

	// 날짜 포맷 설정 (영어(미국)로 설정)
	const options = { year: 'numeric', month: 'long', day: 'numeric' };
	const formatter = new Intl.DateTimeFormat('en-US', options);

	// 포맷된 날짜 출력
	const formattedDate = formatter.format(now);

	//접속날짜 설정
	$('.last-active').text("접속날짜: " + formattedDate);



	//textarea 철자 검사 비활성화
	document.querySelectorAll('textarea').forEach(function(textarea) {
		textarea.setAttribute('spellcheck', 'false');
	});




});



// mainDashBoard 친구 목록 조회
function getFriendList() {

	$.ajax({
		url: "getFriendList",
		type: "POST",
		data: {
			loginUserId: loginUserId
		},
		dataType: "JSON",
		success: function(friendList) {

			let friendListUl = $('.friends-list');
			friendListUl.empty();

			friendList.forEach((friend) => {

				if (friend.urlFilePath == null || friend.urlFilePath == '') {
					friend.urlFilePath = "/uploads/basic_profile.jpg";
				}


				let friendListLi = `
					<li class="friend-item">
						<div class="friend-avatar">
							<img src="${friend.urlFilePath}">
						</div>
						<div class="friend-info">
							<div>
								<p class="friend-name">${friend.userName}</p>
								<p class="friend-id">@${friend.userId}</p>
							</div>
							
							<div class="btn-img-unfollow">
								<button class="btn-unfollow" onclick="unfollowFreind('${friend.userId}')"><img class="unfollow-image" src="/image/friend-unfollow.png" alt="친구삭제"></button>
							</div>
						</div>
					</li>
				`;

				friendListUl.append(friendListLi);
			});
		},
		error: function() {
			alert("mainDashBoard 친구목록 로드 에러");
		}
	});

}


// 친구 삭제(언팔로우)
function unfollowFreind(friendId) {
	if (confirm(friendId + "님을 언팔로우 하시겠습니까?")) {
		$.ajax({
			url: "unfollowFriend",
			type: "POST",
			contentType: 'application/json; charset=utf-8',
			data: JSON.stringify({
				loginUserId: loginUserId,
				friendId: friendId
			}),
			dataType: "text",
			success: function(unfollowText) {

				alert(unfollowText);

				//mainDashBoard에서 친구들 일기 타임라인 조회
				getFriendsDiaryTimeline();

				//mainDashBoard에서 친구 목록 조회
				getFriendList();

			},
			error: function() {
				alert("친구 삭제 서버 에러");
			}
		});
	}


}




// 일기 타임라인 조회
function getFriendsDiaryTimeline() {

	$.ajax({
		url: "getFriendsDiaryTimeline",
		type: "POST",
		data: {
			loginUserId: loginUserId
		},
		dataType: "JSON",
		success: function(friendDiaryProfileList) {


			let timeLineContainer = $('.post_container');
			timeLineContainer.empty();

			friendDiaryProfileList.forEach((friendDiaryProfile) => {

				let dateString = friendDiaryProfile.writeDate;

				const date = new Date(dateString);
				const options = { year: 'numeric', month: 'long', day: 'numeric' };
				const formattedDate = new Intl.DateTimeFormat('en-US', options).format(date);


				if (friendDiaryProfile.urlFilePath == null || friendDiaryProfile.urlFilePath == '') {
					friendDiaryProfile.urlFilePath = "/uploads/basic_profile.jpg";
				}

				let friendDiaryTimeline = `
						<div class="post">
							<div class="post-header">
								<img src="${friendDiaryProfile.urlFilePath}" alt="Profile Picture" class="profile-pic">
									<div class="user-info">
										<p class="username">@${friendDiaryProfile.userId}</p>
										<p class="post-date">${formattedDate}</p>
									</div>
							</div>
												
							<div class="post-content">
								<h1>${friendDiaryProfile.diaryTitle}</h1>
								<p>${friendDiaryProfile.diaryContent}</p>
							</div>
												
						</div>
				`;


				timeLineContainer.append(friendDiaryTimeline);

			});

		},
		error: function() {
			alert("일기 타임라인 로드 에러")
		}

	});
}




// 체크리스트 저장 버튼 이벤트 핸들러
$('#addTodoButton').on('click', addTodo);

// 체크리스트 입력값을 Enter 키로 추가
$('#todoInput').on('keypress', function(event) {
	if (event.key === 'Enter') {
		$('#addTodoButton').click(); // Enter 누르면 button 입력한 것과 동일하게 처리
	}
});



//친구 추천 목록 조회
function viewRecommendList() {
	$.ajax({
		url: "/viewRecommendList",
		type: "POST",
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

					if (recommend.urlFilePath == null || recommend.urlFilePath == '') {
						recommend.urlFilePath = "/uploads/basic_profile.jpg";
					}

					const row = `
					                <tr>
					                    <th>
					                        <div class="image" style="background-image: url('${recommend.urlFilePath}'); background-size: cover; background-position: center;"></div>
					                    </th>
					                    <td>${recommend.userId} ${recommend.userName}</td>
					                    <td><button  class="add-friend-padding"  id="${recommend.userId}" onclick=addFriend('${recommend.userId}') style="background-color: transparent;"><i class="fas fa-user-plus"></i></button></td>
					                </tr>
					            `;
					recommendFriendListTable.append(row);
				});
			} else {
				console.log("recommendList is null");
			}


		},
		error: function() {
			alert("친구추천 에러 발생");
		}
	});
}



//친구 추천 목록 갱신
function refreshRecommendFriendList() {
	viewRecommendList();
}



//친구 신청함 버튼 클릭
$('#requestFriend').click(function() {
	$.ajax({
		url: "/confirmRequestFriend",
		type: "POST",
		data: {
			loginUserId: loginUserId
		},
		success: function(response) {

			checkReceivedFriendRequests(response); //친구 신청리스트 조회

			$('#friendRequestPopup').show(); //친구신청 팝업

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

			if (requestFriend.urlFilePath == null || requestFriend.urlFilePath == '') {
				requestFriend.urlFilePath = "/uploads/basic_profile.jpg";
			}


			let listItem = $('<li class="friend-request-item"></li>');
			listItem.append(`<div class="image" style="background-image: url('${requestFriend.urlFilePath}'); background-size: cover; background-position: center;"></div>`);
			listItem.append(`<div id="${requestFriend.userId}">${requestFriend.userId} ${requestFriend.userName}</div>`);
			listItem.append(`<button class="friend-accept-icon" onclick="receiveFriendRequest('${requestFriend.userId}')"></button>`);


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

			//일기 타임라인 재로드
			getFriendsDiaryTimeline();

			//친구목록 재로드
			getFriendList();

			//친구 추천리스트 재로드
			refreshRecommendFriendList();
		},
		error: function() {
			alert("에러발생");
		}

	});
}



// 전체 체크리스트 조회
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

// 체크리스트 추가
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

			fetchTodoList();
		},
		error: function() {
			alert("에러 발생");
		}
	});
}

// 체크리스트 삭제
function removeTodoItem(li) {

	$.ajax({
		url: "/todoList/remove",
		type: "POST",
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify({
			todoListId: li.attr('id'),
			loginUserId: loginUserId
		}),
		success: function(response) {

			li.remove(); // 체크리스트 삭제

		},
		error: function() {
			alert("todoList 삭제 에러 발생");
		}

	});

}



// 친구 검색
function filterFriends() {
	const input = document.getElementById('name-input').value.toLowerCase(); // 입력된 값을 소문자로 변환하여 저장

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





}



// 친구 목록 생성
function displayFriends(friends) {
	const table = $('#addfriend-list');
	table.empty(); // 기존의 목록을 비움

	friends.forEach(friend => {

		if (friend.urlFilePath == null || friend.urlFilePath == '') {
			friend.urlFilePath = "/uploads/basic_profile.jpg";
		}


		const addFriendButton =
			friend.friend ? `` : `<td><button  class="add-friend-padding" id="${friend.userId}" onclick=addFriend('${friend.userId}') style="background-color: transparent;"><i class="fas fa-user-plus"></i></button></td>`;

		const row = `
                <tr>
                    <th>
                        <div class="image" style="background-image: url('${friend.urlFilePath}'); background-size: cover; background-position: center;"></div>
                    </th>
                    <td>${friend.userId} ${friend.userName}</td>
					
                    ${addFriendButton}
                </tr>
            `;
		table.append(row);
	});
}



// 친구 신청
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




//프로필 사진 업로드 예외처리
function validateForm() {
	var fileInput = document.getElementById('fileInput');
	if (fileInput.files.length === 0) {
		alert("사진을 선택해 주세요.");
		return false;
	}
	return true;
}














