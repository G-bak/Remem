package com.app.controller.todolist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.common.APIResultCode;
import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;
import com.app.service.todolist.TodoListService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // Lombok을 사용한 Logger 자동 생성
public class TodoListController {

	@Autowired
	TodoListService todoListService;

	// 전체 체크리스트 조회
	@ResponseBody
	@PostMapping("/todoList/viewAll")
	public ApiResponse<List<TodoList>> todoListAjax(@RequestParam String loginUserId) {
		log.info("todoListAjax 호출됨 - loginUserId: {}", loginUserId);

		ApiResponse<List<TodoList>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<TodoList> todoList = null;

		try {
			if (loginUserId != null && !loginUserId.isEmpty()) {
				todoList = todoListService.findTodoListByLoginUserId(loginUserId);
				if (todoList != null && !todoList.isEmpty()) {
					log.info("조회된 할 일 목록 수: {}", todoList.size());
					header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
					header.setResultMessage("할 일 목록 조회가 성공적으로 완료되었습니다.");
				} else {
					log.info("조회된 할 일 목록이 없음");
					header.setResultCode(APIResultCode.API_RESULT_NO_DATA);
					header.setResultMessage("조회된 할 일 목록이 없습니다.");
				}
			} else {
				log.warn("로그인된 사용자 ID가 제공되지 않음");
				header.setResultCode(APIResultCode.API_RESULT_MISSING_PARAMETER);
				header.setResultMessage("로그인된 사용자 ID가 제공되지 않았습니다.");
			}
		} catch (Exception e) {
			log.error("할 일 목록 조회 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("할 일 목록 조회 중 오류가 발생하였습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(todoList);
		return apiResponse;
	}

	// 체크리스트 저장
	@ResponseBody
	@PostMapping("/todoList/register")
	public ApiResponse<Integer> todoListRegisterAjax(@RequestBody HashMap<String, String> paramMap) {
		log.info("todoListRegisterAjax 호출됨 - 파라미터: {}", paramMap);

		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		Integer todoListId = null;

		try {
			int result = todoListService.insertTodoList(paramMap);
			if (result > 0) {
				todoListId = todoListService.findTodoListId(paramMap);
				log.info("할 일 등록 성공 - todoListId: {}", todoListId);
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("할 일이 성공적으로 등록되었습니다.");
			} else {
				log.warn("할 일 등록 실패");
				header.setResultCode(APIResultCode.API_RESULT_PROCESSING_ERROR);
				header.setResultMessage("할 일 등록에 실패하였습니다.");
			}
		} catch (Exception e) {
			log.error("할 일 등록 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("할 일 등록 중 오류가 발생하였습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(todoListId);
		return apiResponse;
	}

	// 체크리스트 상태 변경 처리
	@ResponseBody
	@PostMapping("/todoList/checkedOn")
	public ApiResponse<Integer> checkedOnAjax(@RequestBody Map<String, String> paramMap) {
		log.info("checkedOnAjax 호출됨 - 파라미터: {}", paramMap);

		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		int result = 0;

		try {
			if (paramMap == null || paramMap.isEmpty()) {
				log.warn("파라미터가 제공되지 않음");
				header.setResultCode(APIResultCode.API_RESULT_MISSING_PARAMETER);
				header.setResultMessage("파라미터가 제공되지 않았습니다.");
				apiResponse.setHeader(header);
				apiResponse.setBody(result);
				return apiResponse;
			}

			String todoListIdStr = paramMap.get("todoListId");
			String loginUserId = paramMap.get("loginUserId");
			String todoListStatus = paramMap.get("todoListStatus");

			if (todoListIdStr == null || loginUserId == null || todoListStatus == null) {
				log.warn("필수 파라미터 누락 - todoListId: {}, loginUserId: {}, todoListStatus: {}", todoListIdStr, loginUserId,
						todoListStatus);
				header.setResultCode(APIResultCode.API_RESULT_MISSING_PARAMETER);
				header.setResultMessage("필수 파라미터가 누락되었습니다.");
				apiResponse.setHeader(header);
				apiResponse.setBody(result);
				return apiResponse;
			}

			int todoListId = Integer.parseInt(todoListIdStr);
			TodoListUpdate todoListUpdate = new TodoListUpdate(loginUserId, todoListId, todoListStatus);
			result = todoListService.updateTodoListStatus(todoListUpdate);

			if (result > 0) {
				log.info("할 일 상태 업데이트 성공 - todoListId: {}", todoListId);
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("할 일 상태가 성공적으로 업데이트되었습니다.");
			} else {
				log.warn("할 일 상태 업데이트 실패 - todoListId: {}", todoListId);
				header.setResultCode(APIResultCode.API_RESULT_PROCESSING_ERROR);
				header.setResultMessage("할 일 상태 업데이트에 실패하였습니다.");
			}
		} catch (NumberFormatException e) {
			log.error("숫자 형식 오류 - todoListId: {}", paramMap.get("todoListId"), e);
			header.setResultCode(APIResultCode.API_RESULT_INVALID_PARAMETER);
			header.setResultMessage("할 일 ID가 유효하지 않습니다.");
		} catch (Exception e) {
			log.error("할 일 상태 업데이트 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("할 일 상태 업데이트 중 오류가 발생하였습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(result);
		return apiResponse;
	}

	// 체크리스트 삭제 처리
	@ResponseBody
	@PostMapping("/todoList/remove")
	public ApiResponse<Integer> removeTodoListAjax(@RequestBody TodoListRemove todoListRemove) {
		log.info("removeTodoListAjax 호출됨 - 파라미터: {}", todoListRemove);

		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		int result = 0;

		try {
			if (todoListRemove == null) {
				log.warn("요청 본문이 제공되지 않음");
				header.setResultCode(APIResultCode.API_RESULT_MISSING_PARAMETER);
				header.setResultMessage("요청 본문이 제공되지 않았습니다.");
				apiResponse.setHeader(header);
				apiResponse.setBody(result);
				return apiResponse;
			}

			result = todoListService.removeTodoListByTodoListId(todoListRemove);
			if (result > 0) {
				log.info("할 일 제거 성공 - todoListId: {}", todoListRemove.getTodoListId());
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("할 일이 성공적으로 제거되었습니다.");
			} else {
				log.warn("할 일 제거 실패 - todoListId: {}", todoListRemove.getTodoListId());
				header.setResultCode(APIResultCode.API_RESULT_PROCESSING_ERROR);
				header.setResultMessage("할 일 제거에 실패하였습니다.");
			}
		} catch (Exception e) {
			log.error("할 일 제거 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("할 일 제거 중 오류가 발생하였습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(result);
		return apiResponse;
	}
}
