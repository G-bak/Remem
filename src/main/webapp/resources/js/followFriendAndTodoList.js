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
		$('.profile-pics').attr('src', "/image/basic_profile.jpg");
		$('.profile').css('background-image', 'url("/image/basic_profile.jpg")');
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
		success: function(response) {

			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;

			console.log("getFriendList: " + resultCode);

			if (response && response.header && resultCode == "01") {
				let friendListUl = $('.friends-list');
				friendListUl.empty();
			} else if (response && response.header && resultCode == "00") {
				let friendList = response.body;
				let friendListUl = $('.friends-list');
				friendListUl.empty();

				friendList.forEach((friend) => {

					if (friend.urlFilePath == null || friend.urlFilePath == '') {
						friend.urlFilePath = "/image/basic_profile.jpg";
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
			} else {
				console.log("Invalid response header or result code of getFriendList" + resultMsg);
			}


		},
		error: function(error) {
			console.log("Error: " + error);
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
			dataType: "JSON",
			success: function(response) {
				let resultCode = response.header.resultCode;
				let resultMsg = response.header.resultMessage;

				console.log("unfollowFriend: " + resultCode);

				if (response && response.header && resultCode == "00") {
					alert(resultMsg);

					//mainDashBoard에서 친구들 일기 타임라인 조회
					getFriendsDiaryTimeline();

					//mainDashBoard에서 친구 목록 조회
					getFriendList();
				} else {
					console.log("Invalid response header or result code of unfollowFriend" + resultMsg);
				}




			},
			error: function(error) {
				console.log("Error: " + error);
			}
		});
	}


}




// 일기 타임라인 조회
function getFriendsDiaryTimeline() {

	$.ajax({
		url: "/getFriendsDiaryTimeline",
		type: "POST",
		data: {
			loginUserId: loginUserId
		},
		dataType: "JSON",
		success: function(response) {

			let timeLineContainer = $('.post_container');
			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;

			console.log("getFriendsDiaryTimeline: " + resultCode);

			if (response && response.header && resultCode == "01") {
				timeLineContainer.empty();
			} else if (response && response.header && resultCode == "00") {
				let friendDiaryProfileList = response.body;
				timeLineContainer.empty();

				if (friendDiaryProfileList != null && friendDiaryProfileList.length > 0) {
					friendDiaryProfileList.forEach((friendDiaryProfile) => {
						//DB에 저장된 날짜를 가져와서 변환
						let dateString = friendDiaryProfile.writeDate;

						const date = new Date(dateString);
						const options = { year: 'numeric', month: 'long', day: 'numeric' };
						const formattedDate = new Intl.DateTimeFormat('en-US', options).format(date);


						if (friendDiaryProfile.urlFilePath == null || friendDiaryProfile.urlFilePath == '') {
							friendDiaryProfile.urlFilePath = "/image/basic_profile.jpg";
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
				}

			} else {
				console.log("Invalid response header or result code of getFriendsDiaryTimeline" + resultMsg);
			}


		},
		error: function(error) {
			console.log("Error: " + error);
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
		success: function(response) {
			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;

			console.log("viewRecommendList: " + resultCode);
			if (response && response.header && resultCode == "00") {
				let recommendList = response.body;

				const recommendFriendListTable = $(".recommendfriend-list");
				recommendFriendListTable.empty();

				if (recommendList != null && recommendList.length > 0) {
					//tr 동적 추가
					recommendList.forEach((recommend) => {

						if (recommend.urlFilePath == null || recommend.urlFilePath == '') {
							recommend.urlFilePath = "/image/basic_profile.jpg";
						}

						const row = `
						    <tr>
						        <th>
						            <div class="image"
						                style="background-image: url('${recommend.urlFilePath}'); background-size: cover; background-position: center;">
						            </div>
						        </th>
						        <td>${recommend.userId} ${recommend.userName}</td>
						        <td>
						            <button class="add-friend-padding"
						                id="${recommend.userId}"
						                onclick="addFriend('${recommend.userId}')"
						                style="background-color: transparent;">
						                <i class="fas fa-user-plus"></i>
						            </button>
						        </td>
						    </tr>
						`;

						recommendFriendListTable.append(row);
					});
				}
			} else if (resultCode == "01") {
				console.log("추천할 친구가 없습니다.");

				const recommendFriendListTable = $(".recommendfriend-list");
				recommendFriendListTable.empty();

			} else {
				console.log("Invalid response header or result code of viewRecommendList" + resultMsg);
			}


		},
		error: function(error) {
			console.log("Error: " + error);
		}
	});
}



//친구 추천 목록 갱신
//function refreshRecommendFriendList() {
//	viewRecommendList();
//}



//친구 신청함 버튼 클릭
$('#requestFriend').click(function() {
	$.ajax({
		url: "/confirmRequestFriend",
		type: "POST",
		data: {
			loginUserId: loginUserId
		},
		success: function(response) {

			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;


			console.log(`confirmRequestFriend ${resultCode} ${resultMsg}`);
			
			if (response && response.header && resultCode == "00") {
				checkReceivedFriendRequests(response.body); //친구 신청리스트 조회

				$('#friendRequestPopup').show(); //친구신청 팝업

			} else if (response && response.header && resultCode == "01") {
				$('#friendRequestPopup').show(); //친구신청 팝업
			} else {
				console.log("Invalid response header or result code of confirmRequestFriend" + resultMsg);
			}

		},
		error: function(error) {
			console.log("Error: " + error);
		}

	});

});

//친구 신청함 확인
function checkReceivedFriendRequests(responseRequestFriendList) {
	var friendRequestList = $('.friend-request-list'); //ul
	friendRequestList.empty();

	if (responseRequestFriendList != null && responseRequestFriendList.length != 0) {
		responseRequestFriendList.forEach((requestFriend) => {

			if (requestFriend.urlFilePath == null || requestFriend.urlFilePath == '') {
				requestFriend.urlFilePath = "/image/basic_profile.jpg";
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

			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;


			console.log(`receiveFriendRequest ${resultCode} ${resultMsg}`);

			if (response && response.header && resultCode == "01") {
				alert("친구받기완료!");

				var friendRequestList = $('.friend-request-list'); //ul
				friendRequestList.empty();

				//일기 타임라인 재로드
				getFriendsDiaryTimeline();

				//친구목록 재로드
				getFriendList();

				//친구 추천리스트 재로드
				viewRecommendList();

			} else if (response && response.header && resultCode == "00") {
				alert("친구받기완료!");

				checkReceivedFriendRequests(response.body); //친구신청함 비우고 다시 그리기

				//일기 타임라인 재로드
				getFriendsDiaryTimeline();

				//친구목록 재로드
				getFriendList();

				//친구 추천리스트 재로드
				viewRecommendList();
			} else {
				console.log("Invalid response header or result code of receiveFriendRequest" + resultMsg);
			}

		},
		error: function(error) {
			console.log("Error: " + error);
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

			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;

			if (response && response.header && resultCode == "00") {
				const todoList = $('#todoList');
				todoList.empty(); // 기존 리스트를 비움

				let responseTodoList = response.body;

				if (responseTodoList != null && responseTodoList.length > 0) {
					responseTodoList.forEach(todo => {
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
						console.log(`/todoList/remove ${resultCode} ${resultMsg}`);
					});
				}


			} else if (response && response.header && resultCode == "01") {
				const todoList = $('#todoList');
				todoList.empty(); // 기존 리스트를 비움

			} else {
				console.log(`/todoList/viewAll: ${resultCode} ${resultMsg}`);
			}

		},
		error: function(error) {
			console.log("Error: " + error);
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
		dataType: "json",
		success: function(response) {

			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;


			if (response && response.header && resultCode == "00") {
				li.toggleClass('checked', $(checkbox).prop('checked'));
				console.log(`/todoList/checkedOn ${resultCode} ${resultMsg}`);
			} else {
				console.log(`/todoList/checkedOn ${resultCode} ${resultMsg}`);
			}




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
		dataType: "json",
		success: function(response) {

			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;

			if (response && response.header && resultCode == "00") {
				fetchTodoList();
				console.log(`/todoList/register ${resultCode} ${resultMsg}`);
			} else {
				console.log(`/todoList/register ${resultCode} ${resultMsg}`);
			}

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
		dataType: "json",
		success: function(response) {
			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;

			if (response && response.header && resultCode == "00") {
				li.remove(); // 체크리스트 삭제
				console.log(`/todoList/remove ${resultCode} ${resultMsg}`);
			} else {
				console.log(`/todoList/remove ${resultCode} ${resultMsg}`);
			}


		},
		error: function(error) {
			console.log("Error: " + error);
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
			console.log("searchFriend: " + response.header.resultCode);

			let resultCode = response.header.resultCode;
			let resultMsg = response.header.resultMessage;

			if (response && response.header && resultCode == "00") {
				displayFriends(response.body);
			} else if (resultCode == "02") {
				const table = $('#addfriend-list');
				table.empty(); // 기존의 목록을 비움
			} else {
				console.log("Invalid response header or result code of searchFriend" + resultMsg);
			}

		},
		error: function(error) {
			console.log("Error: " + error);
		}
	});





}



// 친구 목록 생성
function displayFriends(friends) {
	const table = $('#addfriend-list');
	table.empty(); // 기존의 목록을 비움

	friends.forEach(friend => {

		if (friend.urlFilePath == null || friend.urlFilePath == '') {
			friend.urlFilePath = "/image/basic_profile.jpg";
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
			success: function(response) {

				let resultCode = response.header.resultCode;
				let resultMsg = response.header.resultMessage;


				console.log("joinRequestFriend: " + resultCode);

				if (response && response.header && resultCode == '00' && response.body > 0) {
					alert(friendId + "님에게 친구신청 완료!");
					$(`button[id="${friendId}"]`).closest('td').remove();

				} else {
					console.log("Invalid response header or result code of joinRequestFriend" + resultCode + resultMsg);

				}


			},
			error: function(error) {
				console.error("Error:", error);
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














