$(document).ready(function() {

   // 사이트 탭 클릭 이벤트
   $('.menu-btn').on('click', function() {
      $('#main-swiper-rightslide').hide();
      $('.swiper-rightslide').hide();
      const target = $($(this).data('target'));
      // 버튼의 data-target이 #content-diary가 아닌 경우 세션 초기화
      if ($(this).data('target') !== '#content-diary') {
         sessionStorage.clear();
         console.log("세션 초기화");
      }
      target.show();
   });

   // 팝업 설정
   setupPopup('#profile-icon', '#profile-popup', '#close-profile-popup');
   setupPopup('#addfriend-icon', '#addfriend-popup', '#close-addfriend-popup');
   setupPopup('#icon_timecapsule', '#popup_timecapsule', '#close_popup_timecapsule');

   // 팝업의 닫기 버튼 클릭 시 팝업 닫기
   $('#popupfriendRequestCloseBtn').click(function() {
      $('#friendRequestPopup').hide();
   });

   // friendRequestPopup 팝업 사라짐
   $('#friendRequestPopup').on('click', function(event) {
      if ($(event.target).is('#friendRequestPopup')) {
         $('#friendRequestPopup').fadeOut();
      }
   });

   // 모든 타임캡슐 로드
   loadAllTimecapsules();





   // 타임캡슐 저장 버튼 클릭 이벤트
   $('#save_popup_timecapsule').on('click', function(event) {
      event.preventDefault();

      var tcDate = $('#date_timecapsule').val();
      var tcContent = $('#input_timecapsule').val();

      var today = new Date();
      var formattedToday = today.toISOString().split('T')[0];

      // 날짜나 내용이 비어 있는지 확인
      if (!tcDate || !tcContent) {
         alert('날짜와 내용을 모두 입력해줘!');
         return;
      }

      // tcDate가 오늘 날짜 이전인지 비교
      if (tcDate < formattedToday) {
         alert("타임캡슐은 오늘 날짜 이전으로 선택이 어려워! 날짜를 다시 선택해줘");
         return;
      }

      $.ajax({
         url: '/save/Timecapsule',
         type: 'POST',
         contentType: 'application/json',
         data: JSON.stringify({
            tcUserId: loginUserId,
            tcDate: tcDate,
            tcContent: tcContent
         }),
         success: function(response) {
            if (response && response.header && (response.header.resultCode === '00')) {
               console.log("Response Code: " + response.header.resultCode);
               console.log("Response Message: " + response.header.resultMessage);

               if (response.header.resultCode === '00') {
                  alert('타임캡슐이 성공적으로 저장되었습니다!');
                  addTimecapsule(response.body.tcDate, response.body.tcContent);

                  // 날짜와 내용 리셋
                  $('#date_timecapsule').val('');
                  $('#input_timecapsule').val('');

                  // 팝업창 닫기
                  $('#popup_timecapsule').fadeOut();
               } else {
                  alert('저장에 실패했습니다.');
                  console.log('Failure Reason:', response.header.resultCode, response.header.resultMessage);
               }
            } else {
               console.log("Invalid response header or result code");
            }
         },
         error: function(error) {
            console.error("Error during AJAX request:", error);
         }
      });
   });


   // 타임캡슐 열기 버튼 클릭 이벤트
   $(document).on('click', '.open_additonal', function() {
      try {
         var content = $(this).data('content');
         var date = $(this).data('date');

         if (!content || !date) {
            console.error("Content or date is missing.");
            return;
         }

         const currentDate = new Date().toISOString().split('T')[0];

         if (date <= currentDate) {
            // 팝업 HTML
            var popupHtml =
               `<div class="popup_html" id="popup_open_timecapsule" 
                style=" position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5); z-index: 5;">
                    <div class="popup_open_content">
                        <p style="font-size: 1.3rem;">타임캡슐이 열렸어요!</p>
                        <p class="secret-msg">아무한테도 말하지 말기 🤫</p>
                        <p id="popup-message" style="font-size: 1rem;">${content}</p>
                        <p class="popup_open_close">닫기</p>
                    </div>
                </div>`;

            // 팝업 추가
            $('body').append(popupHtml);

            // 팝업 표시
            $('#popup_open_timecapsule').fadeIn();

            // 팝업 닫기 이벤트 핸들러
            $(document).off('click', '.popup_open_close').on('click', '.popup_open_close', function() {
               $('#popup_open_timecapsule').fadeOut(function() {
                  $(this).remove();
               });
            });

            $(document).off('click', '#popup_open_timecapsule').on('click', '#popup_open_timecapsule', function(e) {
               if ($(e.target).is('#popup_open_timecapsule')) {
                  $('#popup_open_timecapsule').fadeOut(function() {
                     $(this).remove();
                  });
               }
            });
         } else {
            alert("지정된 날짜 이후에만 타임캡슐을 열 수 있습니다!");
         }
      } catch (error) {
         console.error("An error occurred while opening the time capsule:", error);
      }
   });








   //날짜 선택으로 가계부 조회
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
         success: function(response) {
            console.log(response);


            if (response && response.header && (response.header.resultCode == '00' || response.header.resultCode == '01')) {


              console.log('Full Response:', response); // 전체 응답 객체를 확인
		    console.log('Response Header:', response.header); // 헤더 객체를 확인
		    console.log('Response Result Code:', response.header ? response.header.resultCode : 'No resultCode'); // resultCode를 확인


               const accountContainer = $('.accountModify-popup-content');

               // 기존 버튼 제거
               accountContainer.find('button').remove();

               if (response.body == null) {
                  accountContainer.append('<button id="btn_account_save">저장</button>');
                  registerSaveButtonHandler(); // 저장 버튼 핸들러 등록
               } else {
                  accountContainer.append('<button id="btn_account_modify">수정</button>');
                  registerModifyButtonHandler(); // 수정 버튼 핸들러 등록
               }

               const accountbook = response.body || {};
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
            } else {
               console.log("Invalid response header or result code", response.header.resultCode, response.header.resultMessage);
            }

         },
         error: function() {
            console.error("Error:", error);
         }
      });
   });
});




// 전체 타임캡슐 조회
function loadAllTimecapsules() {
   $.ajax({
      url: '/all/Timecapsules',
      type: 'GET',
      success: function(response) {
         if (response && response.header && (response.header.resultCode === '00' || response.header.resultCode === '01')) {


            console.log("Response Code: " + response.header.resultCode);
            console.log("Response Message:", response.header.resultMessage);
            
             var timecapsules = response.body;
            
            
            timecapsules.forEach(function(tc) {
               if (tc.tcDate && tc.tcContent) {
                  addTimecapsule(tc.tcDate, tc.tcContent);
               } else {
                  console.error("Invalid time capsule data:", tc);
               }
            });
         } else {
			 console.log("Response Code: " + response.header.resultCode);
            console.log("Response Message:", response.header.resultMessage);
		 }
      },
      error: function(error) {
         console.error("Error:", error);
      }
   });
}





// 타임캡슐 추가
function addTimecapsule(date, content) {

   if (!date || !content) {
      console.error("Invalid date or content:", date, content);
      return;
   }

   const additionalContent = $(
      `<div class="additional-content">
                <div class="photo_additional"></div>
                <p class="additional_date"></p>
                <button class="open_additonal" data-content="${content}" data-date="${date}">열기</button>
            </div>`
   );

   $('#content-capsule').prepend(additionalContent);

   // 개별 타임캡슐에 대한 타이머 설정
   additionalContent.data('intervalId', setInterval(function() {
      updateTimer(date, additionalContent);
   }, 1000));
}




// 타임캡슐 카운트 다운 설정
function updateTimer(date, targetElement) {

   if (!date || !targetElement) {
      console.error("Invalid arguments provided to updateTimer.");
      return;
   }

   const future = new Date(date).getTime();
   const now = new Date().getTime();
   const diff = future - now; //카운트 다운 시간 계산

   if (diff <= 0) {
      clearInterval(targetElement.data('intervalId')); // 타이머 멈추기
      targetElement.find(".additional_date").text("이제 타임캡슐 열어도 돼!");
      targetElement.find('.photo_additional').css({
         "background-image": "url('/image/timecapsule_on.png')",
         "background-color": "darkseagreen"
      });
      return;
   }

   const days = Math.floor(diff / (1000 * 60 * 60 * 24));
   const hours = Math.floor((diff / (1000 * 60 * 60)) % 24);
   const mins = Math.floor((diff / (1000 * 60)) % 60);
   const secs = Math.floor((diff / 1000) % 60);

   targetElement.find(".additional_date").html(
      `아직 열면 안돼!<br>${days}일 ${hours}시간 ${mins}분 ${secs}초`
   );
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







// 가계부 저장
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
         incomeTotal: parseFloat($('.income-total').text().replace(/[^0-9.-]/g, '')) || 0,
         foodExpenses: parseFloat($('#food-expenses').val()) || 0,
         traffic: parseFloat($('#traffic').val()) || 0,
         culture: parseFloat($('#culture').val()) || 0,
         clothing: parseFloat($('#clothing').val()) || 0,
         beauty: parseFloat($('#beauty').val()) || 0,
         telecom: parseFloat($('#telecom').val()) || 0,
         membershipFee: parseFloat($('#membership-fee').val()) || 0,
         dailyNecessity: parseFloat($('#daily-necessity').val()) || 0,
         occasions: parseFloat($('#occasions').val()) || 0,
         spendingTotal: parseFloat($('.spending-total').text().replace(/[^0-9.-]/g, '')) || 0,
         incomeSpendingTotal: parseFloat($('#income-spending-total').text().replace(/[^0-9.-]/g, '')) || 0
      }),
      contentType: "application/json; charset=utf-8",
      success: function(response) {
         console.log("Server response:", response);
         if (response && response.header && (response.header.resultCode === '00' )) {


            console.log("Response Code: " + response.header.resultCode);
            console.log("Response Message:", response.header.resultMessage);

            const abs = response.body || {};
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

            // 콜백 함수 호출 (예: 저장 후 수정 버튼으로 변경)
            if (typeof callback === 'function') {
               callback();
            }
         } else {
			console.log("Response Code: " + response.header.resultCode);
            console.log("Response Message:", response.header.resultMessage);
		 }
      },
      error: function(xhr, status, error) {
         console.error("AJAX Error:");
         console.error("Status:", status);
         console.error("Error:", error);
         console.error("Response:", xhr.responseText);
      }
   });
}

//가계부 저장 버튼 핸들러
function registerSaveButtonHandler() {
   $('#btn_account_save').off('click').on('click', function(event) {
      console.log('btn_account_save click');
      saveOrUpdateAccountBook("/save/AccountBook", function() {
         // 저장 후에는 수정 버튼으로 자동 변경
         $('#btn_account_save').remove();
         $('.accountModify-popup-content').append('<button id="btn_account_modify">수정</button>');
         registerModifyButtonHandler();
      });
   });
}

//가계부 수정
function registerModifyButtonHandler() {
   $('#btn_account_modify').off('click').on('click', function(event) {
      console.log('btn_account_modify click');
      saveOrUpdateAccountBook("/modify/AccountBook"); //수정 후 저장
   });
}