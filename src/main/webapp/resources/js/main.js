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

	//날짜 입력값 변경 이벤트
	$('#input_date').on('change', function(event) {
		console.log('input_date change event');
		console.log(event.target.value);

		//Ajax  2024-08-20 data로 담아서 -> 서버 자료 요청

		//서버 param 2024-08-20  -> DB select * from date -> 사용자 전달

		//Ajax 전달받은 값을 확인 -> 각 input 요소에 value로 세팅
	});

	//저장 버튼을 눌렀을때!
	// 입력값 모아서 (json) -> 서버 저장 -> 서버 저장 -> ok , no -> 
	// javascript 전달받은값 화면에 세팅

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