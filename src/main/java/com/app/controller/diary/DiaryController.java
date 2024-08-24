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
// import org.springframework.web.bind.annotation.CrossOrigin;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DiaryController {

	@Autowired
	DiaryService diaryService;  // DiaryService를 주입받아 사용합니다.
	
	/**
	 * 채팅 페이지로 이동합니다.
	 * 
	 * @return 채팅 페이지의 뷰 이름
	 */
	@GetMapping("/chatjao")
	public String chatjao() {
		// 채팅 페이지로 이동하기 위한 뷰의 이름을 반환합니다.
		return "diary/chatjao";
	}
	
	/**
	 * Bot과의 전체 대화 데이터를 삽입하거나 업데이트합니다.
	 * 
	 * @param request 대화 데이터를 포함한 요청 객체
	 * @return 삽입 또는 업데이트 결과를 포함한 API 응답
	 */
	@ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
	@PostMapping("/diary/insertTalkToBotAll")
	public ApiResponse<String> insertTalkToBotAll(@RequestBody TalkToBotAll request) {

		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
		if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", ""); // "question-" 부분을 제거
			dataId = dataId.replace("-", "_"); // "-"를 "_"로 변경
			request.setDataId(dataId); // 변환된 ID를 다시 설정
		}			
		
		int result = 0;  // 결과 값을 저장할 변수 초기화
		if (request.getChatHtml() == null) {
			// ChatHtml이 없으면 새로운 대화 데이터를 삽입합니다.
			result = diaryService.insertTalkToBotAllByQuestion(request);
			if (diaryService.createTalkToBot(request)) {
				// log.info("대화방 테이블 생성 성공, Table ID: {}", request.getDataId());
				// 대화방 테이블 생성 성공 시 로그를 남깁니다.
				System.out.println("대화방 테이블 생성, Table ID: " + request.getDataId());
			} else {
				// log.info("대화방 테이블 생성 실패");
				// 대화방 테이블 생성 실패 시 로그를 남깁니다.
				System.out.println("대화방 테이블 생성 실패");
			}		
		} else {
			// ChatHtml이 존재하면 대화 데이터를 업데이트합니다.
			result = diaryService.updateTalkToBotAllByChat(request);
		}
		
		ApiResponse<String> response = new ApiResponse<String>();  // API 응답 객체 생성
		ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
		response.setBody("DB 저장 데이터 갯수: " + String.valueOf(result));  // 응답 본문에 결과 저장

		// 결과에 따라 응답 헤더의 코드와 메시지를 설정합니다.
		if (result > 0) {
			// log.info("대화 데이터 삽입 또는 업데이트 성공");
			header.setResultCode("00");
			header.setResultMessage("Table: Talk_To_Bot_ALL, 데이터 저장 성공");
		} else {
			// log.info("대화 데이터 삽입 또는 업데이트 실패");
			header.setResultCode("99");
			header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot_ALL, 데이터 저장 실패");
		}
		
		response.setHeader(header);  // 응답 객체에 헤더를 설정
		
		return response;  // 최종적으로 API 응답 객체를 반환합니다.
	}
	
	/**
	 * 사용자 ID로 Bot과의 모든 대화 데이터를 조회합니다.
	 * 
	 * @param request 사용자 ID를 포함한 요청 객체
	 * @return 조회된 대화 데이터를 포함한 API 응답
	 */
	@ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/selectAllData", produces = "application/json")
    public ApiResponse<String> selectAllData(@RequestBody TalkToBotAll request) {
        
		// 사용자 ID를 기반으로 모든 대화 데이터를 조회합니다.
        List<TalkToBotAll> talkToBotAllList = diaryService.selectTalkToBotAllByUserId(request.getUserId());
        // 조회한 대화 데이터 리스트의 세부 데이터를 조회합니다.
        List<List<TalkToBotData>> talkToBotDataList = diaryService.selectAllTalkToBotData(talkToBotAllList);
       
        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성

        JSONArray dataArray = new JSONArray();  // JSON 배열 객체 생성

        // 조회된 대화 데이터를 반복하며 JSON 객체로 변환합니다.
        for (int i = 0; i < talkToBotAllList.size(); i++) {
            JSONObject questionTable = new JSONObject();
            TalkToBotAll allData = talkToBotAllList.get(i);

            questionTable.put("userId", allData.getUserId());  // 사용자 ID 설정
            questionTable.put("roomIndex", allData.getRoomIndex());  // 방 인덱스 설정
            String dataId = allData.getDataId().replace("_", "-");  // 데이터 ID 포맷 변경
            questionTable.put("dataId", dataId);  // 변환된 데이터 ID 설정
            questionTable.put("questionHtml", allData.getQuestionHtml());  // 질문 HTML 설정
            questionTable.put("chatHtml", allData.getChatHtml());  // 채팅 HTML 설정

            JSONArray messages = new JSONArray();  // 메시지 JSON 배열 생성
            for (TalkToBotData data : talkToBotDataList.get(i)) {
                if (data.getUserQuestion() != null) {
                    // 사용자의 질문이 있는 경우 메시지 객체로 변환하여 추가
                    JSONObject userMessage = new JSONObject();
                    userMessage.put("type", data.getUserId());
                    userMessage.put("messageIndex", data.getMessageIndex());
                    userMessage.put("text", data.getUserQuestion());
                    messages.add(userMessage);
                }

                if (data.getBotAnswer() != null) {
                    // Bot의 답변이 있는 경우 메시지 객체로 변환하여 추가
                    JSONObject botAnswer = new JSONObject();
                    botAnswer.put("type", "bot");
                    botAnswer.put("messageIndex", data.getMessageIndex());
                    botAnswer.put("text", data.getBotAnswer());
                    messages.add(botAnswer);
                }
            }

            questionTable.put("messages", messages);  // 메시지 배열을 질문 테이블에 추가
            dataArray.add(questionTable);  // 변환된 질문 테이블을 데이터 배열에 추가
        }

        JSONObject responseBody = new JSONObject();  // 응답 본문 JSON 객체 생성
        responseBody.put("data", dataArray);  // 데이터 배열을 응답 본문에 추가

        // 조회된 데이터가 없는 경우와 있는 경우에 따라 응답 헤더를 설정
        if (talkToBotAllList == null || talkToBotAllList.isEmpty()) {
            // log.info("대화 데이터 없음 또는 조회 실패");
            header.setResultCode("99");
            header.setResultMessage("No data found");
            response.setHeader(header);
            response.setBody(responseBody.toString());
        } else {
            // log.info("대화 데이터 조회 성공: {}개의 데이터", talkToBotAllList.size());
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot_ALL, " + talkToBotAllList.size() + "개의 데이터 불러오기 성공");
            response.setHeader(header);
            response.setBody(responseBody.toString());
        }

        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    }
	
	/**
	 * 데이터 전처리를 수행하고 관련된 다이어리 데이터를 조회합니다.
	 * 
	 * @param request 전처리할 데이터를 포함한 요청 객체
	 * @return 전처리된 데이터와 조회된 다이어리 목록을 포함한 API 응답
	 */
	@ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
	@PostMapping("/diary/PreProcessingData")
	public ApiResponse<List<UserDiary>> PreProcessingData(@RequestBody ExcludedKeyword request) {
	    
	    // 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
	    if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
			dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
			request.setDataId(dataId);  // 변환된 ID를 다시 설정
		}		
		
		// JSON 데이터를 생성하고, 사용자의 질문을 추가합니다.
		JSONObject preProcessingData = new JSONObject();
		preProcessingData.put("question", request.getUserMessage());  // 사용자의 질문을 JSON에 추가
		
		// 삭제할 키워드를 DB에서 가져옵니다.
		List<ExcludedKeyword> excludedKeywordList = diaryService.selectExcludedKeywordsByRoomIdMessageIndex(request);
		if (excludedKeywordList == null) {
		    excludedKeywordList = new ArrayList<>();  // null일 경우 빈 리스트로 초기화
		}

		JSONArray excludedKeywords = new JSONArray();  // 제외할 키워드 JSON 배열 생성
		for (ExcludedKeyword keywordObj : excludedKeywordList) {
		    excludedKeywords.add(keywordObj.getExcludedKeyword());  // 키워드를 배열에 추가
		}
		
		preProcessingData.put("excludedKeywords", excludedKeywords);  // 키워드 배열을 JSON에 추가
		
		// Python 서버의 URL을 설정합니다.
		String url = "http://localhost:5000/process";  // URL 수정

		// 요청 헤더를 설정합니다.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);  // JSON 형식으로 설정

		// 요청 바디를 설정합니다.
		HttpEntity<String> entity = new HttpEntity<>(preProcessingData.toString(), headers);  // JSON 데이터를 요청 바디에 설정

		// RestTemplate을 사용하여 요청을 전송합니다.
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);  // POST 요청을 전송

		// Python 서버의 응답을 처리합니다.
		Map<String, Object> responseBody = response.getBody();  // 응답 본문을 맵으로 가져옴
		String processedData = responseBody != null ? (String) responseBody.get("processed_data") : null;  // 처리된 데이터를 추출

		// log.info("Processed Data: {}", processedData);

		// 처리된 데이터를 띄어쓰기 단위로 분할하여 리스트로 변환합니다.
		String[] wordsArray = processedData.split("\\s+");  // 문자열을 띄어쓰기 단위로 분할
		List<String> wordsList = new ArrayList<>(Arrays.asList(wordsArray));  // 배열을 리스트로 변환

		// DB에서 데이터를 조회합니다.
		List<UserDiary> diaryList = new ArrayList<>();  // 다이어리 리스트 초기화
		if (!wordsList.get(0).isBlank()) {
			diaryList = diaryService.selectDiaryListByKeyword(processedData, request.getUserId());  // 키워드로 다이어리 리스트를 조회
		} else {
			diaryList = null;  // 키워드가 없으면 null로 설정
		}

		// 다이어리 목록이 비어 있으면 키워드를 추가하여 새로운 리스트를 만듭니다.
		if (diaryList == null || diaryList.isEmpty()) {
			diaryList = new ArrayList<>();  // 다이어리 리스트 초기화
			UserDiary dummyDiary = new UserDiary();
			dummyDiary.setKeyword(new ArrayList<>(wordsList));  // 키워드를 추가
			diaryList.add(dummyDiary);  // 더미 다이어리를 리스트에 추가
		} else {
			for (UserDiary diary : diaryList) {
				diary.getKeyword().addAll(wordsList);  // 기존 다이어리에 키워드를 추가
			}
		}

		// API 응답 객체를 생성하고 응답 헤더를 설정합니다.
		ApiResponse<List<UserDiary>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();

		// 다이어리 리스트가 비어 있지 않으면 성공 코드와 메시지를 설정합니다.
		if (diaryList != null) {
			// log.info("데이터 전처리 및 다이어리 조회 성공");
			header.setResultCode("00");
			header.setResultMessage("데이터 전처리 결과 및 다이어리 조회 성공");
			apiResponse.setHeader(header);  // 응답 헤더 설정
			apiResponse.setBody(diaryList);  // 응답 본문에 다이어리 리스트 설정
		} else {
			// log.info("데이터 전처리 또는 다이어리 조회 실패");
			header.setResultCode("01");
			header.setResultMessage("데이터 전처리 결과 및 다이어리 조회 실패");
			apiResponse.setHeader(header);  // 응답 헤더 설정
			apiResponse.setBody(null);  // 응답 본문에 null 설정
		}

		return apiResponse;  // 최종적으로 API 응답 객체를 반환합니다.
	}
	
	/**
	 * Bot과의 대화 데이터를 삽입합니다.
	 * 
	 * @param request 대화 데이터를 포함한 요청 객체
	 * @return 삽입 결과를 포함한 API 응답
	 */
	@ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/insertTalkToBotData", produces = "application/json")
    public ApiResponse<String> insertTalkToBotData(@RequestBody TalkToBotData request) {
        
		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
		if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
			dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
			request.setDataId(dataId);  // 변환된 ID를 다시 설정
		}		

        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
		
		int result = 0;  // 결과 값을 저장할 변수 초기화
		if (request.getBotAnswer() == null) {
			// Bot의 답변이 없는 경우 사용자의 질문 데이터를 삽입합니다.
			result = diaryService.insertUserQuestion(request);
			response.setBody(request.getUserQuestion());  // 응답 본문에 사용자의 질문 설정
		} else { 
			// Bot의 답변이 있는 경우 답변 데이터를 삽입합니다.
			result = diaryService.insertBotAnswer(request);
			response.setBody(request.getBotAnswer());  // 응답 본문에 Bot의 답변 설정
		}
			 
        
        // 결과에 따라 응답 헤더의 코드와 메시지를 설정합니다.
        if (result == 0) {
        	// log.info("대화 데이터 삽입 실패");
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot, 데이터 저장 실패");
        } else {
        	// log.info("대화 데이터 삽입 성공");
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot, 유저 및 봇 대화 데이터 저장 성공");
        }
        
        response.setHeader(header);  // 응답 객체에 헤더를 설정
        
        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    }
    
    /**
     * Bot과의 대화 데이터를 삭제합니다.
     * 
     * @param request 삭제할 데이터를 포함한 요청 객체
     * @return 삭제 결과를 포함한 API 응답
     */
    @ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/dropTable", produces = "application/json")
    public ApiResponse<String> dropTable(@RequestBody TalkToBotAll request) {

		int result1 = 0;  // 첫 번째 결과 값을 저장할 변수 초기화
		int result2 = 0;  // 두 번째 결과 값을 저장할 변수 초기화
		int result3 = 0;  // 세 번째 결과 값을 저장할 변수 초기화
		if (request.getDataId() != null) {
			// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
			String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
			dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
			request.setDataId(dataId);  // 변환된 ID를 다시 설정

			result1 = diaryService.dropTalkToBot(request);  // 대화 테이블을 삭제
			result2 = diaryService.deleteTalkToBotAll(request);  // 모든 대화 데이터를 삭제
			result3 = diaryService.deleteExcludedKeywords(request);  // 제외할 키워드를 삭제
		}		

        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        // 두 번째 결과에 따라 응답 헤더의 코드와 메시지를 설정합니다.
        if (result2 == 0) {
        	// log.info("대화 데이터 삭제 실패");
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot, 삭제 실패");
        } else {
        	// log.info("대화 데이터 삭제 성공");
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot, 삭제 성공");
        }
        response.setBody(String.valueOf(result1));  // 응답 본문에 첫 번째 결과 설정
        response.setHeader(header);  // 응답 객체에 헤더를 설정
        
        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    }
    
    /**
     * 제외할 키워드를 데이터베이스에 추가합니다.
     * 
     * @param request 추가할 키워드를 포함한 요청 객체
     * @return 추가 결과를 포함한 API 응답
     */
    @ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/insertExcludedKeyword", produces = "application/json")
    public ApiResponse<String> insertExcludedKeyword(@RequestBody ExcludedKeyword request) {
        
		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
		String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
		dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
		request.setDataId(dataId);  // 변환된 ID를 다시 설정
		
		int result = diaryService.InsertExcludedKeyword(request);  // 제외할 키워드를 추가

        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        // 결과에 따라 응답 헤더의 코드와 메시지를 설정합니다.
        if (result == 0) {
        	// log.info("키워드 추가 실패");
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: EXCLUDED_KEYWORDS, 데이터 추가 실패");
        } else {
        	// log.info("키워드 추가 성공");
            header.setResultCode("00");
            header.setResultMessage("Table: EXCLUDED_KEYWORDS, 데이터 추가 성공");
        }
        response.setBody(String.valueOf(result));  // 응답 본문에 결과 설정
        response.setHeader(header);  // 응답 객체에 헤더를 설정
        
        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    }
    
    /**
     * 제외할 키워드를 조회합니다.
     * 
     * @param request 조회할 키워드를 포함한 요청 객체
     * @return 조회된 키워드를 포함한 API 응답
     */
    @ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/selectExcludedKeyword", produces = "application/json")
    public ApiResponse<String> selectExcludedKeyword(@RequestBody ExcludedKeyword request) {
        
		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
    	String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
		dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
		request.setDataId(dataId);  // 변환된 ID를 다시 설정
    	
    	List<ExcludedKeyword> excludedKeywordList = null;  // 제외할 키워드 리스트 초기화
    	excludedKeywordList = diaryService.selectExcludedKeyword(request);  // 제외할 키워드 조회

        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        JSONObject data = new JSONObject();  // 데이터 JSON 객체 생성
        JSONArray keywordArray = new JSONArray();  // 키워드 배열 객체 생성
        for (ExcludedKeyword keyword : excludedKeywordList) {
        	JSONObject excludedKeyword = new JSONObject();  // 키워드 JSON 객체 생성
        	excludedKeyword.put("userId", keyword.getUserId());  // 사용자 ID 추가
        	excludedKeyword.put("dataIndex", keyword.getDataIndex());  // 데이터 인덱스 추가
        	excludedKeyword.put("excludedKeyword", keyword.getExcludedKeyword());  // 제외할 키워드 추가

        	keywordArray.add(excludedKeyword);  // 키워드 배열에 추가
        }
        
        data.put("keywordArray", keywordArray);  // 데이터를 응답 본문에 추가

        JSONObject responseBody = new JSONObject();  // 응답 본문 JSON 객체 생성
        responseBody.put("data", data);  // 데이터를 응답 본문에 추가

        // 키워드 리스트가 비어 있으면 실패 코드를 설정하고, 그렇지 않으면 성공 코드를 설정
        if (excludedKeywordList == null || excludedKeywordList.isEmpty()) {
        	// log.info("키워드 데이터 없음 또는 조회 실패");
            header.setResultCode("99");
            header.setResultMessage("No data found");
            response.setHeader(header);  // 응답 객체에 헤더를 설정
            response.setBody(responseBody.toString());  // 응답 본문 설정
        } else {
        	// log.info("키워드 데이터 조회 성공: {}개의 데이터", excludedKeywordList.size());
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot_ALL, " + excludedKeywordList.size() + "개의 데이터 불러오기 성공");
            response.setHeader(header);  // 응답 객체에 헤더를 설정
            response.setBody(responseBody.toString());  // 응답 본문 설정
        }

        return response;  // 최종적으로 API 응답 객체를 반환합니다.      
    }
    
    /**
     * Bot과의 대화 데이터를 업데이트합니다.
     * 
     * @param request 업데이트할 데이터를 포함한 요청 객체
     * @return 업데이트 결과를 포함한 API 응답
     */
    @ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/updateTalkToBotData", produces = "application/json")
    public ApiResponse<String> updateTalkToBotData(@RequestBody TalkToBotData request) {
        
		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
		if (request.getDataId() != null) {
			String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
			dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
			request.setDataId(dataId);  // 변환된 ID를 다시 설정
		}		

        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
		
		int result = 0;  // 결과 값을 저장할 변수 초기화
		if (request.getBotAnswer() != null) {
			// Bot의 답변이 있는 경우 답변 데이터를 업데이트
			result = diaryService.updateBotAnswer(request);
			response.setBody(request.getBotAnswer());  // 응답 본문에 Bot의 답변 설정
		}
        
        // 결과에 따라 응답 헤더의 코드와 메시지를 설정합니다.
        if (result == 0) {
        	// log.info("대화 데이터 업데이트 실패");
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Talk_To_Bot, 데이터 저장 실패");
        } else {
        	// log.info("대화 데이터 업데이트 성공");
            header.setResultCode("00");
            header.setResultMessage("Table: Talk_To_Bot, 유저 및 봇 대화 데이터 저장 성공");
        }
        
        response.setHeader(header);  // 응답 객체에 헤더를 설정
        
        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    }
    
    /**
     * 제외할 키워드를 삭제합니다.
     * 
     * @param request 삭제할 키워드를 포함한 요청 객체
     * @return 삭제 결과를 포함한 API 응답
     */
    @ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/deleteExcludedKeyword", produces = "application/json")
    public ApiResponse<String> deleteExcludedKeyword(@RequestBody ExcludedKeyword request) {

		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
        String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
		dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
		request.setDataId(dataId);  // 변환된 ID를 다시 설정
        
        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        int result = 0;  // 결과 값을 저장할 변수 초기화
		if (request.getDataIndex() != null) {
			// 데이터 인덱스가 존재하는 경우 해당 키워드를 삭제
			result = diaryService.deleteExcludedKeyword(request);
			response.setBody(String.valueOf(result));  // 응답 본문에 결과 설정
		}		
		
        // 결과에 따라 응답 헤더의 코드와 메시지를 설정합니다.
        if (result == 0) {
        	// log.info("키워드 삭제 실패");
            header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Excluded_Keyword, 데이터 삭제 실패");
        } else {
        	// log.info("키워드 삭제 성공");
            header.setResultCode("00");
            header.setResultMessage("Table: Excluded_Keyword, 데이터 삭제 성공");
        }
        
        response.setHeader(header);  // 응답 객체에 헤더를 설정
        
        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    }
    
    /**
     * 제외할 키워드를 조회하고 새로운 키워드로 추가합니다.
     * 
     * @param request 조회할 키워드를 포함한 요청 객체
     * @return 조회 및 추가 결과를 포함한 API 응답
     */
    @ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/selectInsertExcludedKeyword", produces = "application/json")
    public ApiResponse<String> selectInsertExcludedKeyword(@RequestBody ExcludedKeyword request) {
        
		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
        String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
		dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
		request.setDataId(dataId);  // 변환된 ID를 다시 설정
		
		// 데이터 키가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
		String dataKey = request.getDataKey().replace("question-", "");  // "question-" 부분을 제거
		dataKey = dataKey.replace("-", "_");  // "-"를 "_"로 변경
		request.setDataKey(dataKey);  // 변환된 키를 다시 설정
        
        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        List<ExcludedKeyword> excludedKeywordList = null;  // 제외할 키워드 리스트 초기화
        
		if (request.getDataId() != null) {
			// 데이터 ID가 존재하는 경우 제외할 키워드를 조회
			excludedKeywordList = diaryService.selectExcludedKeyword(request);
		}		
		
		int result1 = 0;  // 결과 값을 저장할 변수 초기화
		if (excludedKeywordList != null) {
			// 조회된 키워드 리스트가 존재하면 새로운 키워드로 추가
			for (ExcludedKeyword keyword : excludedKeywordList) {
				keyword.setDataId(request.getDataKey());  // 키워드의 데이터 ID를 변경
				result1 += diaryService.insertExcludedKeywordNotMessageIndex(keyword);  // 메시지 인덱스 제외하고 키워드 추가
			}
		}
		
        // 키워드 리스트가 비어 있지 않으면 성공 코드를 설정하고, 그렇지 않으면 실패 코드를 설정
        if (excludedKeywordList != null) {
        	// log.info("키워드 조회 및 추가 성공");
        	header.setResultCode("00");
            header.setResultMessage("Table: Excluded_Keyword, 데이터 불러오기 및 저장 성공");
            response.setBody(String.valueOf(result1));  // 응답 본문에 결과 설정
        } else {
        	// log.info("키워드 조회 또는 추가 실패");
        	header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: Excluded_Keyword, 데이터 불러오기 또는 저장 실패");
            response.setBody(String.valueOf(result1));  // 응답 본문에 결과 설정
        }
        
        response.setHeader(header);  // 응답 객체에 헤더를 설정
        
        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    } 
    
    /**
     * Bot과의 질문 제목을 수정합니다.
     * 
     * @param request 수정할 제목을 포함한 요청 객체
     * @return 수정 결과를 포함한 API 응답
     */
    @ResponseBody
	// @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping(value = "/diary/updateQuestionTitle", produces = "application/json")
    public ApiResponse<String> updateQuestionTitle(@RequestBody TalkToBotAll request) {
        
		// 데이터 ID가 null이 아닌 경우, 특정 문자열을 제거 및 변환하여 설정합니다.
        String dataId = request.getDataId().replace("question-", "");  // "question-" 부분을 제거
		dataId = dataId.replace("-", "_");  // "-"를 "_"로 변경
		request.setDataId(dataId);  // 변환된 ID를 다시 설정
        
        ApiResponse<String> response = new ApiResponse<>();  // API 응답 객체 생성
        ApiResponseHeader header = new ApiResponseHeader();  // 응답 헤더 객체 생성
        
        int result = 0;  // 결과 값을 저장할 변수 초기화
		if (request.getDataId() != null) {
			// 데이터 ID가 존재하는 경우 질문 제목을 수정
			result = diaryService.updateQuestionTitle(request);
		}		
		
        // 결과에 따라 응답 헤더의 코드와 메시지를 설정합니다.
        if (result > 0) {
        	// log.info("질문 제목 수정 성공");
        	header.setResultCode("00");
            header.setResultMessage("Table: talk_to_bot_all, 질문 제목 수정 성공");
            response.setBody(String.valueOf(result));  // 응답 본문에 결과 설정
        } else {
        	// log.info("질문 제목 수정 실패");
        	header.setResultCode("99");
            header.setResultMessage("SQL 오류 >> Table: talk_to_bot_all, 질문 제목 수정 실패");
            response.setBody(String.valueOf(result));  // 응답 본문에 결과 설정
        }
        
        response.setHeader(header);  // 응답 객체에 헤더를 설정
        
        return response;  // 최종적으로 API 응답 객체를 반환합니다.         
    }
	
}
