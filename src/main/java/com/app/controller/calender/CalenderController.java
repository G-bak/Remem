package com.app.controller.calender;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.dto.calender.Calender;
import com.app.dto.calender.CalenderFriends;
import com.app.dto.calender.CalenderMemoDiary;
import com.app.dto.calender.Friends;
import com.app.dto.diary.UserDiary;
import com.app.service.calender.CalenderService;
import com.app.service.diary.WriteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Controller
public class CalenderController {
	
	@Autowired
	CalenderService calenderService;
	
	@Autowired
	WriteService writeService;
    
    @GetMapping("/calender")
    public String index() {
        return "calender/calender";
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/selectUserName")
    public ApiResponse<String> selectUserName(@RequestBody Calender request) {
        String userId = request.getUserId();
        
        String userName = calenderService.selectUserNameByUserId(userId);
//        System.out.println("User Name: " + userName);

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        response.setBody(userName.toString());
        
        if (userName != null) {
			header.setResultCode("00");
			header.setResultMessage("Table: Users, 유저 데이터 불러오기 성공");
		} else {
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: Users, 유저 데이터 불러오기 실패");
		}

        response.setHeader(header);

        return response;
    }

    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/insertCalender")
    public ApiResponse<String> insertCalender(@RequestBody Calender request) {
        String dataId = request.getDataId();
        String userId = request.getUserId();
        String calenderTitle = request.getCalenderTitle();
        List<String> friendIdList = request.getFriendId();
        List<String> friendNameList = request.getFriendName();
//        System.out.println("Friend ID List Size: " + friendIdList.size());
//        System.out.println("Friend Name List Size: " + friendNameList.size());

//        System.out.println("Data ID: " + dataId);
//        System.out.println("User ID: " + userId);
//        System.out.println("Calender Title: " + calenderTitle);
        
//        for (int i = 0; i < friendIdList.size(); i++) {
//            System.out.println("참석자 " + (i + 1) + ": " + friendIdList.get(i));
//        }
        
        if (friendIdList.size() > 0) {
        	request.setHasFriends("Y");
        } else {
        	request.setHasFriends("N");
        }

        int result1 = 0;
        int result2 = 0;
        
        for (int i = 0; i <= friendIdList.size(); i++) {
            result1 += calenderService.insertCalender(request);
            if (i < friendIdList.size()) {  // 마지막 반복일 때는 실행되지 않도록 함
                request.setUserId(friendIdList.get(i));
            }
        }
        
        friendIdList.add(userId);
        String userName = calenderService.selectUserNameByUserId(userId);
//        System.out.println("User Name: " + userName);
        friendNameList.add(userName);
        for (int i = 0; i < friendIdList.size(); i++) {
        	Map<String, String> friendData = new HashMap<>();
        	friendData.put("dataId", dataId);
        	friendData.put("friendId", friendIdList.get(i));
            friendData.put("friendName", friendNameList.get(i));
            result2 += calenderService.insertCalenderFriends(friendData);
            
//            if (result2 > 0) {
//                System.out.println("일정 친구 추가 성공: " + friendNameList.get(i));
//            } else {
//                System.out.println("일정 친구 추가 실패: " + friendNameList.get(i));
//            }
        }

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result1));
        
        if (result1 > 0) {
        	System.out.println("일정 친구 추가 성공");
			header.setResultCode("00");
			header.setResultMessage("Table: calender, 데이터 저장 성공");
		} else {
			System.out.println("일정 친구 추가 실패");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: calender, 데이터 저장 실패");
		}

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/insertCalenderDetail")
    public ApiResponse<String> insertCalenderDetail(@RequestBody CalenderMemoDiary request) {
        String dataId = request.getDataId();
        String readerId = request.getReaderId();
        String diaryContent = request.getDiaryContent();
//        System.out.println("Data ID: " + dataId);
//        System.out.println("Reader ID: " + readerId);
//        System.out.println("Diary Content: " + diaryContent);

        int result = 0;
        
        if (dataId != null) {
            result = calenderService.insertCalenderDetail(request);
        }
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));
        
        if (result > 0) {
        	System.out.println("Table: calender_memo_diary, 데이터 저장 성공: " + result);
			header.setResultCode("00");
			header.setResultMessage("Table: calender_memo_diary, 데이터 저장 성공: " + result);
		} else {
			System.out.println("SQL 오류 >> Table: calender_memo_diary, 데이터 저장 실패");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: calender_memo_diary, 데이터 저장 실패");
		}

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/loadAllData")
//    @PostMapping(value = "/calender/loadAllData", produces = "application/json")
    public ApiResponse<String> loadAllData(@RequestBody Calender request) {
        String userId = request.getUserId();
//        System.out.println("User ID: " + userId);

        List<Calender> calenderList = null;
        calenderList = calenderService.selectCalender(request);
        
//        if (calenderList != null) {
//            System.out.println("일정 갯수: " + calenderList.size());
//        }
        
        // JSON 객체 생성
        JSONObject data = new JSONObject();
        JSONArray calenders = new JSONArray();
        
        for (int i = 0; i < calenderList.size(); i++) {
        	JSONObject calender = new JSONObject();
//        	System.out.println("Calender Date: " + calenderList.get(i).getDataId());
        	calender.put("calenderDate", calenderList.get(i).getCalenderDate());
        	calender.put("dataId", calenderList.get(i).getDataId());
        	calender.put("calenderTitle", calenderList.get(i).getCalenderTitle());
        	
        	calenders.add(calender);
        }
        
        data.put("calenders", calenders);
        
        JSONObject responseBody = new JSONObject();
        responseBody.put("data", data);

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        
        if (calenderList != null) {
        	System.out.println("일정 데이터 불러오기 성공: " + calenderList.size());
			header.setResultCode("00");
			header.setResultMessage("Table: calender, 불러온 데이터 갯수: " + calenderList.size());
			response.setBody(responseBody.toString());
        } else {
			System.out.println("일정 데이터 없음");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: calender, 데이터 불러오기 실패 또는 데이터 없음");
			response.setBody(responseBody.toString());
        }

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/deleteCalender")
    public ApiResponse<String> deleteCalender(@RequestBody Calender request) {
        String dataId = request.getDataId();
        String userId = request.getUserId();

//        System.out.println("Data ID: " + dataId);
//        System.out.println("User ID: " + userId);

        // 실제 처리 로직을 추가 (예: 데이터베이스에 저장).
        int result1 = 0;
        int result2 = 0;
        result1 = calenderService.deleteCalender(request);
//        System.out.println("Result1: " + result1);
        if (result1 > 0) {
        	result2 = calenderService.deleteCalenderFriends(request);
        }
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result1));
        
        if (result1 > 0) {
        	System.out.println("일정 삭제 성공, 일정에 포함된 친구를 삭제한 인원: " + result2);
			header.setResultCode("00");
			header.setResultMessage("Table: calender, 데이터 삭제 성공");
		} else {
			System.out.println("일정 삭제 실패");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: calender, 데이터 삭제 실패");
		}

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/selectFriends")
    public ApiResponse<String> selectFriends(@RequestBody Friends request) {
        String userId = request.getUserId();
//        System.out.println("User ID: " + userId);

        List<Friends> friendList = null;
        friendList = calenderService.selectFriends(request);
        
//        if (friendList != null) {
//            System.out.println("친구 인원 수: " + friendList.size());
//        }
        
        // JSON 객체 생성
        JSONObject data = new JSONObject();
        JSONArray friends = new JSONArray();
        
        for (int i = 0; i < friendList.size(); i++) {
        	JSONObject friend = new JSONObject();
//        	System.out.println("Friend ID: " + friendList.get(i).getFriendId());
        	friend.put("friendId", friendList.get(i).getFriendId());
        	friend.put("userName", friendList.get(i).getUserName());
        	
        	friends.add(friend);
        }
        
        data.put("friends", friends);
        
        JSONObject responseBody = new JSONObject();
        responseBody.put("data", data);

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        
        if (friendList != null) {
        	System.out.println("친구 데이터 불러오기 성공: " + friendList.size());
			header.setResultCode("00");
			header.setResultMessage("Table: friend_list, 불러온 데이터 갯수: " + friendList.size());
			response.setBody(responseBody.toString());
        } else {
			System.out.println("일정 데이터 없음");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: friend_list, 데이터 불러오기 실패 또는 데이터 없음");
			response.setBody(responseBody.toString());
        }

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/showFriendList")
    public ApiResponse<String> showFriendList(@RequestBody CalenderFriends request) {
        String userId = request.getUserId();
        String dataId = request.getDataId();
//        System.out.println("User ID: " + userId);
//        System.out.println("Data ID: " + dataId);

        List<CalenderFriends> friendList = null;
        friendList = calenderService.showFriendList(request);
        
//        if (friendList != null) {
//            System.out.println("친구 인원 수: " + friendList.size());
//        }
        
        // JSON 객체 생성
        JSONObject data = new JSONObject();
        JSONArray friends = new JSONArray();
        
        for (int i = 0; i < friendList.size(); i++) {
//        	System.out.println(userId + " | " + friendList.get(i).getFriendId());
        	if (!userId.equals(friendList.get(i).getFriendId())) {
//        		System.out.println("Friend Name: " + friendList.get(i).getFriendName());
        		JSONObject friend = new JSONObject();
//            	System.out.println("Friend ID: " + friendList.get(i).getFriendId());
        		friend.put("readerName", friendList.get(i).getReaderName());
            	friend.put("friendId", friendList.get(i).getFriendId());
            	friend.put("friendName", friendList.get(i).getFriendName());
            	
            	friends.add(friend);
        	}
        }
        
        data.put("friends", friends);
        
        JSONObject responseBody = new JSONObject();
        responseBody.put("data", data);

        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        
        if (friendList != null) {
        	System.out.println("친구 데이터 불러오기 성공: " + (friendList.size()-1) + "명");
			header.setResultCode("00");
			header.setResultMessage("Table: calender_friends, 불러온 데이터 갯수: " + friendList.size());
			response.setBody(responseBody.toString());
        } else {
			System.out.println("친구 데이터 없음");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: calender_friends, 데이터 불러오기 실패 또는 데이터 없음");
			response.setBody(responseBody.toString());
        }

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/selectCalenderDetail")
    public ApiResponse<String> selectCalenderDetail(@RequestBody CalenderMemoDiary request) {
        String dataId = request.getDataId();
//        System.out.println("Data ID: " + dataId);

        CalenderMemoDiary calenderDetail = null;
        calenderDetail = calenderService.selectCalenderDetail(request);
        
        JSONObject data = new JSONObject();
        if (calenderDetail != null) {
        	// JSON 객체 생성
        	JSONObject calenderInfo = new JSONObject();
        	calenderInfo.put("dataId", calenderDetail.getDataId());
        	calenderInfo.put("readerId", calenderDetail.getReaderId());
        	calenderInfo.put("appointmentTime", calenderDetail.getAppointmentTime());
        	calenderInfo.put("memoContent", calenderDetail.getMemoContent());
        	calenderInfo.put("diaryTitle", calenderDetail.getDiaryTitle());
        	calenderInfo.put("diaryContent", calenderDetail.getDiaryContent());
        	
        	data.put("calenderInfo", calenderInfo);
        }

        JSONObject responseBody = new JSONObject();
        responseBody.put("data", data);
//        System.out.println(responseBody);
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        
        if (calenderDetail != null) {
        	System.out.println("일정 상세 데이터 불러오기 성공");
			header.setResultCode("00");
			header.setResultMessage("Table: calender_memo_diary, 일정 상세 데이터 불러오기 성공");
			response.setBody(responseBody.toString());
        } else {
			System.out.println("일정 상세 데이터 불러오기 실패");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: calender_memo_diary, 일정 상세 데이터 불러오기 실패");
			response.setBody(null);
        }

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/calender/updateCalenderDetail")
    public ApiResponse<String> updateCalenderDetail(@RequestBody CalenderMemoDiary request) {
        String dataId = request.getDataId();
        String appointmentTime = request.getAppointmentTime();
        String memoContent = request.getMemoContent();
        String diaryTitle = request.getDiaryTitle();
        String diaryContent = request.getDiaryContent();
//        System.out.println("Data ID: " + dataId);
//        System.out.println("Appointment Time: " + appointmentTime);
//        System.out.println("Memo Content: " + memoContent);
//        System.out.println("Diary Title: " + diaryTitle);
//        System.out.println("Diary Content: " + diaryContent);

        int result = 0;
        
        if (dataId != null) {
//        	System.out.println("calenderService.updateCalenderDetail 호출됨");
            result = calenderService.updateCalenderDetail(request);
        }
        
//        System.out.println("Result: " + result);
        
        // 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));
        
        if (result > 0) {
        	System.out.println("Table: calender_memo_diary, 데이터 수정 성공: " + result);
			header.setResultCode("00");
			header.setResultMessage("Table: calender_memo_diary, 데이터 수정 성공: " + result);
		} else {
			System.out.println("SQL 오류 >> Table: calender_memo_diary, 데이터 수정 실패");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: calender_memo_diary, 데이터 수정 실패");
		}

        response.setHeader(header);

        return response;
    }
    
    @ResponseBody
    // @CrossOrigin(origins = "http://127.0.0.1:5501")
	@PostMapping("/saveMyDiary")
	public ApiResponse<String> saveMyDiary(@RequestBody UserDiary request) {
//		System.out.println("User ID: " + request.getUserId());
//		System.out.println("Write Date: " + request.getWriteDate());
//		System.out.println("Diary Title: " + request.getDiaryTitle());
//		System.out.println("Diary Content: " + request.getDiaryContent());
		
		String userId = request.getUserId();
		String writeDate = request.getWriteDate();
		String title = request.getDiaryTitle();
		String content = request.getDiaryContent();
		int result = 0;

		boolean allPresent = Stream.of(userId, writeDate, title, content).allMatch(Objects::nonNull);

		if (allPresent) {
		    result = writeService.insertUserDiary(request);
		} else {
//			System.out.println("DB 전송 실패, 파라미터가 NULL 값인지 확인하세요");
		}
		
//		System.out.println("Result: " + result);
		
		// 응답 데이터 작성
        ApiResponse<String> response = new ApiResponse<String>();
        ApiResponseHeader header =  new ApiResponseHeader();
        response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));
        
        if (result > 0) {
        	System.out.println("Table: user_diary, 데이터 저장 성공: " + result);
			header.setResultCode("00");
			header.setResultMessage("Table: user_diary, 데이터 저장 성공: " + result);
		} else {
			System.out.println("SQL 오류 >> Table: user_diary, 데이터 저장 실패");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: user_diary, 데이터 저장 실패");
		}

        response.setHeader(header);

        return response;
	}
}
