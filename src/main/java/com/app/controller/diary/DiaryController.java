package com.app.controller.diary;

import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.dto.diary.ExcludedKeyword;
import com.app.dto.diary.TalkToBotAll;
import com.app.dto.diary.TalkToBotData;
import com.app.dto.diary.UserDiary;
import com.app.service.diary.DiaryService;

@Controller
public class DiaryController {
	
	@Autowired
	DiaryService diaryService;
	
	@GetMapping("/chatjao")
	public String chatjao() {
		return "diary/chatjao";
	}
	
	@ResponseBody
//	@CrossOrigin(origins = "http://127.0.0.1:5501")
	@PostMapping("/diary/insertTalkToBotAll")
	public ApiResponse<String> insertTalkToBotAll(@RequestBody TalkToBotAll request) {
//		 System.out.println("User ID: " + request.getUserId());
//		 System.out.println("Room Index: " + request.getRoomIndex());
//		 System.out.println("Question HTML Code: " + request.getQuestionHtml());
//		 System.out.println("Chat HTML Code: " + request.getChatHtml());
//		 System.out.println("Data ID: " + request.getDataId());
		
		if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");
			dataId = dataId.replace("-", "_");
			request.setDataId(dataId);
//			  System.out.println("Replace Data ID: " + dataId);
		}			
		
		int result = 0;
		if (request.getChatHtml() == null) {
			result = diaryService.insertTalkToBotAllByQuestion(request);
			if (diaryService.createTalkToBot(request)) {
//				  System.out.println("대화방 테이블 생성, Table ID: " + request.getDataId());
			} else {
//				  System.out.println("대화방 테이블 생설 실패");
			}		
		} else {
			result = diaryService.updateTalkToBotAllByChat(request);
		}
		
		ApiResponse<String> response = new ApiResponse<String>();
		ApiResponseHeader header = new ApiResponseHeader();
		response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));
		
		if (result > 0) {
			header.setResultCode("00");
			header.setResultMessage("Table: Talk_To_Bot_ALL, 데이터 저장 성공");
		} else {
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot_ALL, 데이터 저장 실패");
		}
		
		response.setHeader(header);
		
		return response;		 
	}
	
	@ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/selectAllData", produces = "application/json")
    public ApiResponse<String> selectAllData(@RequestBody TalkToBotAll request) {
//         System.out.println("User ID: " + request.getUserId());
        
        List<TalkToBotAll> talkToBotAllList = diaryService.selectTalkToBotAllByUserId(request.getUserId());
        
        for (int i = 0; i < talkToBotAllList.size(); i++) {
//        	  System.out.println("User ID: " + talkToBotAllList.get(i).getUserId());
//        	  System.out.println("Room Index: " + talkToBotAllList.get(i).getRoomIndex());
//        	  System.out.println("Data ID: " + talkToBotAllList.get(i).getDataId());
//        	  System.out.println("Question HTML: " + talkToBotAllList.get(i).getQuestionHtml());
//        	  System.out.println("Chat HTML: " + talkToBotAllList.get(i).getChatHtml());
        }
        
        List<List<TalkToBotData>> talkToBotDataList = diaryService.selectAllTalkToBotData(talkToBotAllList);
        
       for (int i = 0; i < talkToBotDataList.size(); i++) {
//    	     System.out.println("Talk To Bot, Table " + (i+1));
    	   for (int j = 0; j < talkToBotDataList.get(i).size(); j++) {
//    		     System.out.println("Message No: " + talkToBotDataList.get(i).get(j).getMessageIndex());
//    		     System.out.println("Data ID: " + talkToBotDataList.get(i).get(j).getDataId());
//    		     System.out.println("User ID: " + talkToBotDataList.get(i).get(j).getUserId());
//    		     System.out.println("Room Index: " + talkToBotDataList.get(i).get(j).getRoomIndex());
//    		     System.out.println("Messsage Index: " + talkToBotDataList.get(i).get(j).getMessageIndex());
//    		     System.out.println("Bot Answer: " + talkToBotDataList.get(i).get(j).getBotAnswer());
//    		     System.out.println("User Question: " + talkToBotDataList.get(i).get(j).getUserQuestion());
    	   }
       }
       
       ApiResponse<String> response = new ApiResponse<>();
       ApiResponseHeader header = new ApiResponseHeader();

       JSONArray dataArray = new JSONArray();

       for (int i = 0; i < talkToBotAllList.size(); i++) {
           JSONObject questionTable = new JSONObject();
           TalkToBotAll allData = talkToBotAllList.get(i);

           questionTable.put("userId", allData.getUserId());
           questionTable.put("roomIndex", allData.getRoomIndex());
           String dataId = allData.getDataId().replace("_", "-");
           questionTable.put("dataId", dataId);
           questionTable.put("questionHtml", allData.getQuestionHtml());
           questionTable.put("chatHtml", allData.getChatHtml());

           JSONArray messages = new JSONArray();
           for (TalkToBotData data : talkToBotDataList.get(i)) {
               if (data.getUserQuestion() != null) {
                   JSONObject userMessage = new JSONObject();
                   userMessage.put("type", data.getUserId());
                   userMessage.put("messageIndex", data.getMessageIndex());
                   userMessage.put("text", data.getUserQuestion());
                   messages.add(userMessage);
               }

               if (data.getBotAnswer() != null) {
                   JSONObject botAnswer = new JSONObject();
                   botAnswer.put("type", "bot");
                   botAnswer.put("messageIndex", data.getMessageIndex());
                   botAnswer.put("text", data.getBotAnswer());
                   messages.add(botAnswer);
               }
           }

           questionTable.put("messages", messages);
           dataArray.add(questionTable);
       }

       JSONObject responseBody = new JSONObject();
       responseBody.put("data", dataArray);

       if (talkToBotAllList == null || talkToBotAllList.isEmpty()) {
           header.setResultCode("99");
           header.setResultMessage("No data found");
           response.setHeader(header);
           response.setBody(responseBody.toString());
       } else {
           header.setResultCode("00");
           header.setResultMessage("Table: Talk_To_Bot_ALL, " + talkToBotAllList.size() + "개의 데이터 불러오기 성공");
           response.setHeader(header);
           response.setBody(responseBody.toString()); // JSON 데이터를 보기 좋게 포맷팅 (4는 들여쓰기 수준)
       }

       return response;         
    }
	
	@ResponseBody
//	@CrossOrigin(origins = "http://127.0.0.1:5501")
	@PostMapping("/diary/PreProcessingData")
	public ApiResponse<List<UserDiary>> PreProcessingData(@RequestBody ExcludedKeyword request) {
//		System.out.println("User ID: " + request.getUserId());
//		  System.out.println("PreProcessingData: ");
//		  System.out.println("Data ID: " + request.getDataId());
//		  System.out.println("Room ID: " + request.getRoomId());
//	      System.out.println("User Message: " + request.getUserMessage());
	    
	    if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");
			dataId = dataId.replace("-", "_");
			request.setDataId(dataId);
//			  System.out.println("Replace Data ID: " + dataId);
		}		
		
		// JSON 데이터 생성
		JSONObject preProcessingData = new JSONObject();
		preProcessingData.put("question", request.getUserMessage());
		
		// 삭제할 키워드를 DB에서 가져오기
		List<ExcludedKeyword> excludedKeywordList = diaryService.selectExcludedKeywordsByRoomIdMessageIndex(request);
		if (excludedKeywordList == null) {
		    excludedKeywordList = new ArrayList<>(); // null일 경우 빈 리스트로 초기화
		}

		JSONArray excludedKeywords = new JSONArray();
		for (ExcludedKeyword keywordObj : excludedKeywordList) {
//			  System.out.println("keywordObj.getExcludedKeyword(): "+keywordObj.getExcludedKeyword());
		    excludedKeywords.add(keywordObj.getExcludedKeyword()); // ExcludedKeyword 객체에서 문자열 추출
		}
		
		preProcessingData.put("excludedKeywords", excludedKeywords);
//		  System.out.println(preProcessingData.toString());
		
//		 삭제해야할 키워드 리스트 db에서 가져와서 JSON 객체에 담기
		// Python 서버 URL
		String url = "http://localhost:5000/process"; // URL 수정

		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 요청 바디 설정
		 HttpEntity<String> entity = new HttpEntity<>(preProcessingData.toString(), headers);

		// RestTemplate을 사용하여 요청 전송
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

		// Python 서버 응답 처리
		Map<String, Object> responseBody = response.getBody();
		String processedData = responseBody != null ? (String) responseBody.get("processed_data") : null; // 응답 필드 수정

		// 응답을 로그로 출력
		// // System.out.println("Processed Data: " + processedData);

		// 데이터 전처리된 String 문자를 띄어쓰기 단위로 나누고 리스트에 새로 담기
		// 문자열을 띄어쓰기 단위로 분할
		String[] wordsArray = processedData.split("\\s+");

		// 배열을 리스트로 변환
		List<String> wordsList = new ArrayList<>(Arrays.asList(wordsArray));

		// DB 데이터 받기
		List<UserDiary> diaryList = new ArrayList<UserDiary>();
		if (!wordsList.get(0).isBlank()) {
			diaryList = diaryService.selectDiaryListByKeyword(processedData, request.getUserId());
		} else {
			diaryList = null;
		}

		// 다이어리 목록이 비어 있는 경우에도 키워드를 추가
		if (diaryList == null || diaryList.isEmpty()) {
			diaryList = new ArrayList<>();
			UserDiary dummyDiary = new UserDiary();
			dummyDiary.setKeyword(new ArrayList<>(wordsList)); // 키워드 추가
			diaryList.add(dummyDiary);
		} else {
			for (UserDiary diary : diaryList) {
				diary.getKeyword().addAll(wordsList);
			}
		}

		// API 응답 설정
		ApiResponse<List<UserDiary>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();

		if (diaryList != null) {
			header.setResultCode("00");
			header.setResultMessage("데이터 전처리 결과 및 다이어리 조회 성공");
			apiResponse.setHeader(header);
			apiResponse.setBody(diaryList);
		} else {
			header.setResultCode("01");
			header.setResultMessage("데이터 전처리 결과 및 다이어리 조회 실패");
			apiResponse.setHeader(header);
			apiResponse.setBody(null);
		}

		return apiResponse;
	}
	
	@ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/insertTalkToBotData", produces = "application/json")
    public ApiResponse<String> insertTalkToBotData(@RequestBody TalkToBotData request) {
//         System.out.println("Data ID: " + request.getDataId());
//		 System.out.println("User ID: " + request.getUserId());
//		 System.out.println("Room Index: " + request.getRoomIndex());
//		 System.out.println("Message Index: " + request.getMessageIndex());
//		 System.out.println("Bot Answer: " + request.getBotAnswer());
//		 System.out.println("User Question: " + request.getUserQuestion());
        
		if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");
			dataId = dataId.replace("-", "_");
			request.setDataId(dataId);
//			 System.out.println("Replace Data ID: " + dataId);
		}		

        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
		
		int result = 0;
		if (request.getBotAnswer() == null) {
			result = diaryService.insertUserQuestion(request);
			response.setBody(request.getUserQuestion());
		} else { 
			result = diaryService.insertBotAnswer(request);
			response.setBody(request.getBotAnswer()); 
		}
			 
        
        if (result == 0) {
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot, 데이터 저장 실패");
        } else {
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot, 유저 및 봇 대화 데이터 저장 성공");
        }
        
        response.setHeader(header);
        
        return response;         
    }
    
    @ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/dropTable", produces = "application/json")
    public ApiResponse<String> dropTable(@RequestBody TalkToBotAll request) {
//         System.out.println("Data ID: " + request.getDataId());
//		 System.out.println("User ID: " + request.getUserId());
//		 System.out.println("Room Index: " + request.getRoomIndex());
        
		int result1 = 0;
		int result2 = 0;
		int result3 = 0;
		if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");
			dataId = dataId.replace("-", "_");
			request.setDataId(dataId);
//			 System.out.println("Replace Data ID: " + dataId);
			
			result1 = diaryService.dropTalkToBot(request);
			result2 = diaryService.deleteTalkToBotAll(request);
			result3 = diaryService.deleteExcludedKeywords(request);
//			 System.out.println("Result 2: " + result2);
//			 System.out.println("Result 3: " + result3);
		}		

        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
        
        if (result2 == 0) {
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot, 삭제 실패");
        } else {
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot, 삭제 성공");
        }
        response.setBody(String.valueOf(result1));
        response.setHeader(header);
        
        return response;         
    }
    
    @ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/insertExcludedKeyword", produces = "application/json")
    public ApiResponse<String> insertExcludedKeyword(@RequestBody ExcludedKeyword request) {
//    	 System.out.println("User ID: " + request.getUserId());
//    	 System.out.println("Room ID: " + request.getRoomId());
//    	 System.out.println("Data Index: " + request.getDataIndex());
//    	 System.out.println("Message Index: " + request.getMessageIndex());
//		 System.out.println("Data ID: " + request.getDataId());
//		 System.out.println("Excluded Keyword: " + request.getExcludedKeyword());
        
		String dataId = request.getDataId().replace("question-", "");
		dataId = dataId.replace("-", "_");
		request.setDataId(dataId);
		
		int result = diaryService.InsertExcludedKeyword(request);
//		 System.out.println(result);

        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
        
        if (result == 0) {
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: EXCLUDED_KEYWORDS, 데이터 추가 실패");
        } else {
            header.setResultCode("00");
            header.setResultMessage("Table: EXCLUDED_KEYWORDS, 데이터 추가 성공");
        }
        response.setBody(String.valueOf(result));
        response.setHeader(header);
        
        return response;         
    }
    
    @ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/selectExcludedKeyword", produces = "application/json")
    public ApiResponse<String> selectExcludedKeyword(@RequestBody ExcludedKeyword request) {
//    	 System.out.println("User ID: " + request.getUserId());
//    	 System.out.println("Data ID: " + request.getDataId());
        
    	String dataId = request.getDataId().replace("question-", "");
		dataId = dataId.replace("-", "_");
		request.setDataId(dataId);
    	
    	List<ExcludedKeyword> excludedKeywordList = null;
    	
    	excludedKeywordList = diaryService.selectExcludedKeyword(request);
//		 System.out.println(excludedKeywordList.size());

        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
        
        JSONObject data = new JSONObject();
        JSONArray keywordArray = new JSONArray();
        for (int i = 0; i < excludedKeywordList.size(); i++) {
        	JSONObject excludedKeyword = new JSONObject();
        	excludedKeyword.put("userId", excludedKeywordList.get(i).getUserId());
        	excludedKeyword.put("dataIndex", excludedKeywordList.get(i).getDataIndex());
        	excludedKeyword.put("excludedKeyword", excludedKeywordList.get(i).getExcludedKeyword());

        	keywordArray.add(excludedKeyword);
        }
        
        data.put("keywordArray", keywordArray);

        JSONObject responseBody = new JSONObject();
        responseBody.put("data", data);

        if (excludedKeywordList == null || excludedKeywordList.isEmpty()) {
            header.setResultCode("99");
            header.setResultMessage("No data found");
            response.setHeader(header);
            response.setBody(responseBody.toString());
        } else {
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot_ALL, " + excludedKeywordList.size() + "개의 데이터 불러오기 성공");
            response.setHeader(header);
            response.setBody(responseBody.toString()); // JSON 데이터를 보기 좋게 포맷팅 (4는 들여쓰기 수준)
        }

        return response;      
    }
    
    @ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/updateTalkToBotData", produces = "application/json")
    public ApiResponse<String> updateTalkToBotData(@RequestBody TalkToBotData request) {
//    	 System.out.println("# UpdateTalkToBotData: ");
//         System.out.println("Data ID: " + request.getDataId());
//		 System.out.println("User ID: " + request.getUserId());
//		 System.out.println("Room Index: " + request.getRoomIndex());
//		 System.out.println("Message Index: " + request.getMessageIndex());
//		 System.out.println("Bot Answer: " + request.getBotAnswer());
        
		if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");
			dataId = dataId.replace("-", "_");
			request.setDataId(dataId);
//			 System.out.println("Replace Data ID: " + dataId);
		}		

        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
		
		int result = 0;
		if (request.getBotAnswer() != null) {
			result = diaryService.updateBotAnswer(request);
//			 System.out.println("Result: " + result);
			response.setBody(request.getBotAnswer());
		}
        
        if (result == 0) {
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot, 데이터 저장 실패");
        } else {
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot, 유저 및 봇 대화 데이터 저장 성공");
        }
        
        response.setHeader(header);
        
        return response;         
    }
    
    @ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/deleteExcludedKeyword", produces = "application/json")
    public ApiResponse<String> deleteExcludedKeyword(@RequestBody ExcludedKeyword request) {
//    	 System.out.println("\n# DeleteExcludedKeyword: ");
//    	 System.out.println("User ID: " + request.getUserId());
//    	 System.out.println("Data Id: " + request.getDataId());
//         System.out.println("Data Index: " + request.getDataIndex());
        
        String dataId = request.getDataId().replace("question-", "");
		dataId = dataId.replace("-", "_");
		request.setDataId(dataId);
//		 System.out.println("Replace Data ID: " + dataId);
        
        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
        
        int result = 0;
		if (request.getDataIndex() != null) {
			result = diaryService.deleteExcludedKeyword(request);
//			 System.out.println("Result: " + result);
			response.setBody(String.valueOf(result));
		}		
		
        if (result == 0) {
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Excluded_Keyword, 데이터 삭제 실패");
        } else {
            header.setResultCode("00");
            header.setResultMessage("Table: Excluded_Keyword, 데이터 삭제 성공");
        }
        
        response.setHeader(header);
        
        return response;         
    }
    
    @ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/selectInsertExcludedKeyword", produces = "application/json")
    public ApiResponse<String> selectInsertExcludedKeyword(@RequestBody ExcludedKeyword request) {
//    	 System.out.println("\n# SelectDeleteInsertExcludedKeyword: ");
//    	 System.out.println("User ID: " + request.getUserId());
//         System.out.println("Data Id: " + request.getDataId());
//         System.out.println("Data Key: " + request.getDataKey());
        
        String dataId = request.getDataId().replace("question-", "");
		dataId = dataId.replace("-", "_");
		request.setDataId(dataId);
//		 System.out.println("Replace Data ID: " + dataId);
		
		String dataKey = request.getDataKey().replace("question-", "");
		dataKey = dataKey.replace("-", "_");
		request.setDataKey(dataKey);
//		 System.out.println("Replace Data Key: " + dataKey);
        
        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
        
        List<ExcludedKeyword> excludedKeywordList = null;
        
		if (request.getDataId() != null) {
			excludedKeywordList = diaryService.selectExcludedKeyword(request);
//			 System.out.println("Excluded Keyword List: " + excludedKeywordList.size());
		}		
		
		int result1 = 0;
		if (excludedKeywordList != null) {
			for (int i = 0; i < excludedKeywordList.size(); i++) {
				excludedKeywordList.get(i).setDataId(request.getDataKey());
				result1 += diaryService.insertExcludedKeywordNotMessageIndex(excludedKeywordList.get(i));
			}
//			 System.out.println("Result1: " + result1);
		}
		
        if (excludedKeywordList != null) {
        	header.setResultCode("00");
            header.setResultMessage("Table: Excluded_Keyword, 데이터 불러오기 및 저장 성공");
            response.setBody(String.valueOf(result1));
        } else {
        	header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Excluded_Keyword, 데이터 불러오기 또는 저장 실패");
            response.setBody(String.valueOf(result1));
        }
        
        response.setHeader(header);
        
        return response;         
    } 
    
    @ResponseBody
//    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/updateQuestionTitle", produces = "application/json")
    public ApiResponse<String> updateQuestionTitle(@RequestBody TalkToBotAll request) {
//    	 System.out.println("\n# SelectDeleteInsertExcludedKeyword: ");
//    	 System.out.println("User ID: " + request.getUserId());
//         System.out.println("Data Id: " + request.getDataId());
//         System.out.println("Question Item HTML: " + request.getQuestionHtml());
        
        String dataId = request.getDataId().replace("question-", "");
		dataId = dataId.replace("-", "_");
		request.setDataId(dataId);
//		 System.out.println("Replace Data ID: " + dataId);
        
        ApiResponse<String> response = new ApiResponse<>();
        ApiResponseHeader header = new ApiResponseHeader();
        
        int result = 0;
		if (request.getDataId() != null) {
			result = diaryService.updateQuestionTitle(request);
//			 System.out.println("Result " + result);
		}		
		
        if (result > 0) {
        	header.setResultCode("00");
            header.setResultMessage("Table: talk_to_bot_all, 질문 제목 수정 성공");
            response.setBody(String.valueOf(result));
        } else {
        	header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: talk_to_bot_all, 질문 제목 수정 실패");
            response.setBody(String.valueOf(result));
        }
        
        response.setHeader(header);
        
        return response;         
    }
	
}