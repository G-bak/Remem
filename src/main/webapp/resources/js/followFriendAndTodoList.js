$(document).ready(function() {
	
	// 오늘 할일 전체 조회하는 ajax
	fetchTodoList();


	// 친구 추천 목록
	viewRecommendList();
	
	

});



// 저장 버튼을 눌렀을 때!
$('#addTodoButton').on('click', addTodo);

// 입력값을 Enter 키로 추가
$('#todoInput').on('keypress', function(event) {
	if (event.key === 'Enter') {
		$('#addTodoButton').click(); // Enter 누르면 button 입력한 것과 동일하게 처리
	}
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
					                        <div class="image" style="background-image: url('${recommend.urlFilePath}'); background-size: cover; background-position: center;"></div>
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
function refreshRecommendFriendList() {
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
			listItem.append(`<div class="image" style="background-image: url('${requestFriend.urlFilePath}'); background-size: cover; background-position: center;"></div>`);
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

// 오늘 할 일 추가 처리
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


	


}




function displayFriends(friends) {
	const table = $('#addfriend-list');
	table.empty(); // 기존의 목록을 비움

	friends.forEach(friend => {

		const addFriendButton =
			friend.friend ? `` : `<td><button id="${friend.userId}" onclick=addFriend('${friend.userId}')><i class="fas fa-user-plus"></i></button></td>`;

		const row = `
                <tr>
                    <th>
                        <div class="image" style="background-image: url('${friend.urlFilePath}'); background-size: cover; background-position: center;"></div>
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









