package com.app.controller.calender;

import org.json.simple.JSONArray;  // JSON 배열을 다루기 위한 라이브러리
import org.json.simple.JSONObject;  // JSON 객체를 다루기 위한 라이브러리
import org.springframework.beans.factory.annotation.Autowired;  // 스프링에서 의존성 주입을 위한 어노테이션
import org.springframework.http.HttpStatus;  // HTTP 상태 코드를 다루기 위한 라이브러리
import org.springframework.http.ResponseEntity;  // HTTP 응답을 다루기 위한 객체
import org.springframework.stereotype.Controller;  // 스프링 MVC에서 컨트롤러 역할을 하는 어노테이션
import org.springframework.web.bind.annotation.CrossOrigin;  // CORS 설정을 위한 어노테이션
import org.springframework.web.bind.annotation.GetMapping;  // GET 요청을 처리하는 어노테이션
import org.springframework.web.bind.annotation.PostMapping;  // POST 요청을 처리하는 어노테이션
import org.springframework.web.bind.annotation.RequestBody;  // 요청 본문을 매핑하는 어노테이션
import org.springframework.web.bind.annotation.ResponseBody;  // 응답 본문을 매핑하는 어노테이션

import com.app.dto.api.ApiResponse;  // API 응답을 위한 객체
import com.app.dto.api.ApiResponseHeader;  // API 응답 헤더를 위한 객체
import com.app.dto.calender.Calender;  // 캘린더 DTO 객체
import com.app.dto.calender.CalenderFriends;  // 캘린더 친구 DTO 객체
import com.app.dto.calender.CalenderMemoDiary;  // 캘린더 메모 및 다이어리 DTO 객체
import com.app.dto.calender.Friends;  // 친구 DTO 객체
import com.app.dto.diary.UserDiary;  // 사용자 다이어리 DTO 객체
import com.app.service.calender.CalenderService;  // 캘린더 서비스 인터페이스
import com.app.service.diary.WriteService;  // 다이어리 작성 서비스 인터페이스

import java.util.HashMap;  // 해시맵을 다루기 위한 라이브러리
import java.util.List;  // 리스트를 다루기 위한 라이브러리
import java.util.Map;  // 맵을 다루기 위한 라이브러리
import java.util.Objects;  // 객체를 다루기 위한 라이브러리
import java.util.stream.Stream;  // 스트림 API를 사용하기 위한 라이브러리

import lombok.extern.slf4j.Slf4j;  // 로깅을 위한 롬복 어노테이션

@Slf4j  // 로깅 기능을 추가
@Controller  // 해당 클래스를 컨트롤러로 선언
public class CalenderController {

	@Autowired
	CalenderService calenderService;  // 캘린더 서비스 객체를 주입받음
	
	@Autowired
	WriteService writeService;  // 다이어리 작성 서비스 객체를 주입받음
    
    /**
     * 캘린더 페이지로 이동합니다.
     * 
     * @return 캘린더 페이지의 뷰 이름
     */
    @GetMapping("/calender")
    public String index() {
        return "calender/calender";  // "calender" 페이지로 이동
    }
    
    /**
     * 사용자 이름을 조회합니다.
     * 
     * @param request 사용자 ID를 포함한 요청 객체
     * @return 사용자 이름을 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/selectUserName")
    public ApiResponse<String> selectUserName(@RequestBody Calender request) {
        String userId = request.getUserId();  // 요청 객체에서 사용자 ID 추출
        String userName = calenderService.selectUserNameByUserId(userId);  // 서비스에서 사용자 이름 조회

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        response.setBody(userName.toString());  // 응답 본문에 사용자 이름 설정
        
        if (userName != null) {
            // log.info("사용자 이름 조회 성공: {}", userName);  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: Users, 유저 데이터 불러오기 성공");  // 성공 메시지 설정
		} else {
            // log.info("사용자 이름 조회 실패: userId={}", userId);  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: Users, 유저 데이터 불러오기 실패");  // 실패 메시지 설정
		}

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }

    /**
     * 새로운 캘린더 항목을 삽입합니다.
     * 
     * @param request 삽입할 캘린더 정보
     * @return 삽입 결과를 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/insertCalender")
    public ApiResponse<String> insertCalender(@RequestBody Calender request) {
        String dataId = request.getDataId();  // 요청 객체에서 데이터 ID 추출
        String userId = request.getUserId();  // 요청 객체에서 사용자 ID 추출
        String calenderTitle = request.getCalenderTitle();  // 요청 객체에서 캘린더 제목 추출
        List<String> friendIdList = request.getFriendId();  // 요청 객체에서 친구 ID 리스트 추출
        List<String> friendNameList = request.getFriendName();  // 요청 객체에서 친구 이름 리스트 추출
        
        if (friendIdList.size() > 0) {
        	request.setHasFriends("Y");  // 친구가 있는 경우 "Y"로 설정
        } else {
        	request.setHasFriends("N");  // 친구가 없는 경우 "N"으로 설정
        }

        int result1 = 0;  // 캘린더 삽입 결과를 저장할 변수
        int result2 = 0;  // 친구 삽입 결과를 저장할 변수
        
        for (int i = 0; i <= friendIdList.size(); i++) {
            result1 += calenderService.insertCalender(request);  // 캘린더 정보 삽입
            if (i < friendIdList.size()) {  // 마지막 반복일 때는 실행되지 않도록 함
                request.setUserId(friendIdList.get(i));  // 친구 ID를 설정
            }
        }
        
        friendIdList.add(userId);  // 친구 ID 리스트에 사용자 ID 추가
        String userName = calenderService.selectUserNameByUserId(userId);  // 사용자 이름 조회

        friendNameList.add(userName);  // 친구 이름 리스트에 사용자 이름 추가
        for (int i = 0; i < friendIdList.size(); i++) {
        	Map<String, String> friendData = new HashMap<>();  // 친구 데이터를 담을 맵 생성
        	friendData.put("dataId", dataId);  // 데이터 ID 설정
        	friendData.put("friendId", friendIdList.get(i));  // 친구 ID 설정
            friendData.put("friendName", friendNameList.get(i));  // 친구 이름 설정
            result2 += calenderService.insertCalenderFriends(friendData);  // 친구 정보 삽입
        }

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result1));  // 응답 본문에 삽입된 데이터 갯수 설정
        
        if (result1 > 0) {
            // log.info("캘린더 및 친구 데이터 삽입 성공: {}", result1);  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: calender, 데이터 저장 성공");  // 성공 메시지 설정
		} else {
            // log.info("캘린더 및 친구 데이터 삽입 실패");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: calender, 데이터 저장 실패");  // 실패 메시지 설정
		}

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 캘린더의 세부 정보를 삽입합니다.
     * 
     * @param request 삽입할 캘린더 메모 및 다이어리 정보
     * @return 삽입 결과를 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/insertCalenderDetail")
    public ApiResponse<String> insertCalenderDetail(@RequestBody CalenderMemoDiary request) {
        String dataId = request.getDataId();  // 요청 객체에서 데이터 ID 추출
        String readerId = request.getReaderId();  // 요청 객체에서 읽은 사용자 ID 추출
        String diaryContent = request.getDiaryContent();  // 요청 객체에서 다이어리 내용 추출

        int result = 0;  // 삽입 결과를 저장할 변수
        
        if (dataId != null) {
            result = calenderService.insertCalenderDetail(request);  // 캘린더 세부 정보 삽입
        }
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));  // 응답 본문에 삽입된 데이터 갯수 설정
        
        if (result > 0) {
            // log.info("캘린더 세부 정보 삽입 성공: {}", result);  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: calender_memo_diary, 데이터 저장 성공: " + result);  // 성공 메시지 설정
		} else {
            // log.info("캘린더 세부 정보 삽입 실패");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: calender_memo_diary, 데이터 저장 실패");  // 실패 메시지 설정
		}

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 모든 캘린더 데이터를 로드합니다.
     * 
     * @param request 사용자 ID를 포함한 요청 객체
     * @return 로드된 캘린더 데이터를 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/loadAllData")
//    @PostMapping(value = "/calender/loadAllData", produces = "application/json")
    public ApiResponse<String> loadAllData(@RequestBody Calender request) {
        String userId = request.getUserId();  // 요청 객체에서 사용자 ID 추출

        List<Calender> calenderList = null;  // 캘린더 리스트 초기화
        calenderList = calenderService.selectCalender(request);  // 캘린더 데이터 조회
        
        // JSON 객체 생성
        JSONObject data = new JSONObject();  // JSON 객체 생성
        JSONArray calenders = new JSONArray();  // JSON 배열 생성
        
        for (int i = 0; i < calenderList.size(); i++) {
        	JSONObject calender = new JSONObject();  // 개별 캘린더 데이터를 담을 JSON 객체 생성

        	calender.put("calenderDate", calenderList.get(i).getCalenderDate());  // 캘린더 날짜 추가
        	calender.put("dataId", calenderList.get(i).getDataId());  // 데이터 ID 추가
        	calender.put("calenderTitle", calenderList.get(i).getCalenderTitle());  // 캘린더 제목 추가
        	
        	calenders.add(calender);  // JSON 배열에 추가
        }
        
        data.put("calenders", calenders);  // 최종 JSON 객체에 추가
        
        JSONObject responseBody = new JSONObject();  // 응답 본문 JSON 객체 생성
        responseBody.put("data", data);  // 본문에 데이터 추가

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        if (calenderList != null) {
            // log.info("모든 캘린더 데이터 로드 성공: {}", calenderList.size());  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: calender, 불러온 데이터 갯수: " + calenderList.size());  // 성공 메시지 설정
			response.setBody(responseBody.toString());  // 응답 본문 설정
        } else {
            // log.info("캘린더 데이터 없음 또는 로드 실패");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: calender, 데이터 불러오기 실패 또는 데이터 없음");  // 실패 메시지 설정
			response.setBody(responseBody.toString());  // 응답 본문 설정
        }

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 캘린더 데이터를 삭제합니다.
     * 
     * @param request 삭제할 캘린더 정보를 포함한 요청 객체
     * @return 삭제 결과를 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/deleteCalender")
    public ApiResponse<String> deleteCalender(@RequestBody Calender request) {
        String dataId = request.getDataId();  // 요청 객체에서 데이터 ID 추출
        String userId = request.getUserId();  // 요청 객체에서 사용자 ID 추출

        // 실제 처리 로직을 추가 (예: 데이터베이스에 저장).
        int result1 = 0;  // 캘린더 삭제 결과를 저장할 변수
        int result2 = 0;  // 친구 삭제 결과를 저장할 변수
        result1 = calenderService.deleteCalender(request);  // 캘린더 삭제

        if (result1 > 0) {
        	result2 = calenderService.deleteCalenderFriends(request);  // 친구 삭제
        }
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result1));  // 응답 본문에 삭제된 데이터 갯수 설정
        
        if (result1 > 0) {
            // log.info("캘린더 삭제 성공, 포함된 친구 삭제 성공: {}", result2);  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: calender, 데이터 삭제 성공");  // 성공 메시지 설정
		} else {
            // log.info("캘린더 삭제 실패");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: calender, 데이터 삭제 실패");  // 실패 메시지 설정
		}

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 친구 목록을 조회합니다.
     * 
     * @param request 조회할 친구 목록 정보
     * @return 조회된 친구 목록을 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/selectFriends")
    public ApiResponse<String> selectFriends(@RequestBody Friends request) {
        String userId = request.getUserId();  // 요청 객체에서 사용자 ID 추출

        List<Friends> friendList = null;  // 친구 목록 초기화
        friendList = calenderService.selectFriends(request);  // 친구 목록 조회
        
        // JSON 객체 생성
        JSONObject data = new JSONObject();  // JSON 객체 생성
        JSONArray friends = new JSONArray();  // JSON 배열 생성
        
        for (int i = 0; i < friendList.size(); i++) {
        	JSONObject friend = new JSONObject();  // 개별 친구 데이터를 담을 JSON 객체 생성

        	friend.put("friendId", friendList.get(i).getFriendId());  // 친구 ID 추가
        	friend.put("userName", friendList.get(i).getUserName());  // 친구 이름 추가
        	
        	friends.add(friend);  // JSON 배열에 추가
        }
        
        data.put("friends", friends);  // 최종 JSON 객체에 추가
        
        JSONObject responseBody = new JSONObject();  // 응답 본문 JSON 객체 생성
        responseBody.put("data", data);  // 본문에 데이터 추가

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        if (friendList != null) {
            // log.info("친구 데이터 조회 성공: {}", friendList.size());  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: friend_list, 불러온 데이터 갯수: " + friendList.size());  // 성공 메시지 설정
			response.setBody(responseBody.toString());  // 응답 본문 설정
        } else {
            // log.info("친구 데이터 조회 실패 또는 데이터 없음");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: friend_list, 데이터 불러오기 실패 또는 데이터 없음");  // 실패 메시지 설정
			response.setBody(responseBody.toString());  // 응답 본문 설정
        }

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 캘린더에 연결된 친구 목록을 조회합니다.
     * 
     * @param request 조회할 캘린더 친구 정보
     * @return 조회된 친구 목록을 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/showFriendList")
    public ApiResponse<String> showFriendList(@RequestBody CalenderFriends request) {
        String userId = request.getUserId();  // 요청 객체에서 사용자 ID 추출
        String dataId = request.getDataId();  // 요청 객체에서 데이터 ID 추출

        List<CalenderFriends> friendList = null;  // 캘린더 친구 목록 초기화
        friendList = calenderService.showFriendList(request);  // 캘린더 친구 목록 조회
        
        // JSON 객체 생성
        JSONObject data = new JSONObject();  // JSON 객체 생성
        JSONArray friends = new JSONArray();  // JSON 배열 생성
        
        for (int i = 0; i < friendList.size(); i++) {

        	if (!userId.equals(friendList.get(i).getFriendId())) {  // 친구 ID가 사용자 ID와 다른 경우에만 실행

        		JSONObject friend = new JSONObject();  // 개별 친구 데이터를 담을 JSON 객체 생성

        		friend.put("readerName", friendList.get(i).getReaderName());  // 읽은 사용자 이름 추가
            	friend.put("friendId", friendList.get(i).getFriendId());  // 친구 ID 추가
            	friend.put("friendName", friendList.get(i).getFriendName());  // 친구 이름 추가
            	
            	friends.add(friend);  // JSON 배열에 추가
        	}
        }
        
        data.put("friends", friends);  // 최종 JSON 객체에 추가
        
        JSONObject responseBody = new JSONObject();  // 응답 본문 JSON 객체 생성
        responseBody.put("data", data);  // 본문에 데이터 추가

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        if (friendList != null) {
            // log.info("캘린더 친구 목록 조회 성공: {}명", friendList.size()-1);  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: calender_friends, 불러온 데이터 갯수: " + friendList.size());  // 성공 메시지 설정
			response.setBody(responseBody.toString());  // 응답 본문 설정
        } else {
            // log.info("캘린더 친구 목록 조회 실패 또는 데이터 없음");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: calender_friends, 데이터 불러오기 실패 또는 데이터 없음");  // 실패 메시지 설정
			response.setBody(responseBody.toString());  // 응답 본문 설정
        }

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 캘린더 세부 정보를 조회합니다.
     * 
     * @param request 조회할 캘린더 메모 다이어리 정보
     * @return 조회된 세부 정보를 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/selectCalenderDetail")
    public ApiResponse<String> selectCalenderDetail(@RequestBody CalenderMemoDiary request) {
        String dataId = request.getDataId();  // 요청 객체에서 데이터 ID 추출

        CalenderMemoDiary calenderDetail = null;  // 캘린더 세부 정보 초기화
        calenderDetail = calenderService.selectCalenderDetail(request);  // 캘린더 세부 정보 조회
        
        JSONObject data = new JSONObject();  // JSON 객체 생성
        if (calenderDetail != null) {
        	// JSON 객체 생성
        	JSONObject calenderInfo = new JSONObject();  // 개별 캘린더 정보를 담을 JSON 객체 생성
        	calenderInfo.put("dataId", calenderDetail.getDataId());  // 데이터 ID 추가
        	calenderInfo.put("readerId", calenderDetail.getReaderId());  // 읽은 사용자 ID 추가
        	calenderInfo.put("appointmentTime", calenderDetail.getAppointmentTime());  // 약속 시간 추가
        	calenderInfo.put("memoContent", calenderDetail.getMemoContent());  // 메모 내용 추가
        	calenderInfo.put("diaryTitle", calenderDetail.getDiaryTitle());  // 다이어리 제목 추가
        	calenderInfo.put("diaryContent", calenderDetail.getDiaryContent());  // 다이어리 내용 추가
        	
        	data.put("calenderInfo", calenderInfo);  // 최종 JSON 객체에 추가
        }

        JSONObject responseBody = new JSONObject();  // 응답 본문 JSON 객체 생성
        responseBody.put("data", data);  // 본문에 데이터 추가
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        if (calenderDetail != null) {
            // log.info("캘린더 세부 정보 조회 성공");  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: calender_memo_diary, 일정 상세 데이터 불러오기 성공");  // 성공 메시지 설정
			response.setBody(responseBody.toString());  // 응답 본문 설정
        } else {
            // log.info("캘린더 세부 정보 조회 실패");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: calender_memo_diary, 일정 상세 데이터 불러오기 실패");  // 실패 메시지 설정
			response.setBody(null);  // 응답 본문 설정 (실패 시)
        }

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 캘린더 세부 정보를 업데이트합니다.
     * 
     * @param request 업데이트할 캘린더 메모 다이어리 정보
     * @return 업데이트 결과를 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
    @PostMapping("/calender/updateCalenderDetail")
    public ApiResponse<String> updateCalenderDetail(@RequestBody CalenderMemoDiary request) {
        String dataId = request.getDataId();  // 요청 객체에서 데이터 ID 추출
        String appointmentTime = request.getAppointmentTime();  // 요청 객체에서 약속 시간 추출
        String memoContent = request.getMemoContent();  // 요청 객체에서 메모 내용 추출
        String diaryTitle = request.getDiaryTitle();  // 요청 객체에서 다이어리 제목 추출
        String diaryContent = request.getDiaryContent();  // 요청 객체에서 다이어리 내용 추출

        int result = 0;  // 업데이트 결과를 저장할 변수
        
        if (dataId != null) {
            result = calenderService.updateCalenderDetail(request);  // 캘린더 세부 정보 업데이트
        }
        
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));  // 응답 본문에 업데이트된 데이터 갯수 설정
        
        if (result > 0) {
            // log.info("캘린더 세부 정보 업데이트 성공: {}", result);  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: calender_memo_diary, 데이터 수정 성공: " + result);  // 성공 메시지 설정
		} else {
            // log.info("캘린더 세부 정보 업데이트 실패");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: calender_memo_diary, 데이터 수정 실패");  // 실패 메시지 설정
		}

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
    }
    
    /**
     * 사용자의 다이어리를 저장합니다.
     * 
     * @param request 저장할 사용자 다이어리 정보
     * @return 저장 결과를 포함한 API 응답
     */
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")  // CORS 설정 (필요시 사용)
	@PostMapping("/saveMyDiary")
	public ApiResponse<String> saveMyDiary(@RequestBody UserDiary request) {
		
		String userId = request.getUserId();  // 요청 객체에서 사용자 ID 추출
		String writeDate = request.getWriteDate();  // 요청 객체에서 작성일자 추출
		String title = request.getDiaryTitle();  // 요청 객체에서 다이어리 제목 추출
		String content = request.getDiaryContent();  // 요청 객체에서 다이어리 내용 추출
		int result = 0;  // 저장 결과를 저장할 변수

		// 모든 필드가 null이 아닌지 확인
		boolean allPresent = Stream.of(userId, writeDate, title, content).allMatch(Objects::nonNull);

		if (allPresent) {
		    result = writeService.insertUserDiary(request);  // 다이어리 데이터베이스에 삽입
		} else {
            // log.info("DB 전송 실패, 파라미터가 NULL 값인지 확인하세요");  // 로깅 (주석 처리)
			System.out.println("DB 전송 실패, 파라미터가 NULL 값인지 확인하세요");
		}
		
		// 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
        ApiResponseHeader header =  new ApiResponseHeader();  // 응답 헤더 객체 생성
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));  // 응답 본문에 저장된 데이터 갯수 설정
        
        if (result > 0) {
            // log.info("다이어리 데이터 저장 성공: {}", result);  // 로깅 (주석 처리)
			header.setResultCode("00");  // 성공 코드 설정
			header.setResultMessage("Table: user_diary, 데이터 저장 성공: " + result);  // 성공 메시지 설정
		} else {
            // log.info("다이어리 데이터 저장 실패");  // 로깅 (주석 처리)
			header.setResultCode("99");  // 실패 코드 설정
			header.setResultMessage("SQL 오류 >> Table: user_diary, 데이터 저장 실패");  // 실패 메시지 설정
		}

        response.setHeader(header);  // 응답 객체에 헤더 설정

        return response;  // 응답 반환
	}
}
