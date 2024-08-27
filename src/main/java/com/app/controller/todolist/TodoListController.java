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

import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;
import com.app.service.todolist.TodoListService;

@Controller
public class TodoListController {

	@Autowired
	TodoListService todoListService;

	// 전체 체크리스트 조회
	@ResponseBody
	@PostMapping("/todoList/viewAll")
	public ApiResponse<List<TodoList>> todoListAjax(@RequestParam String loginUserId) {
		ApiResponse<List<TodoList>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<TodoList> todoList = null;

		try {
			// 로그인 사용자 ID가 유효한지 확인
			if (loginUserId != null && !loginUserId.isEmpty()) {
				// 로그인 사용자 ID로 할 일 목록 조회
				todoList = todoListService.findTodoListByLoginUserId(loginUserId);

				// 결과에 따른 응답 설정
				if (todoList != null && !todoList.isEmpty()) {
					header.setResultCode("00");
					header.setResultMessage("할 일 목록 조회가 성공적으로 완료되었습니다.");
				} else {
					header.setResultCode("02");
					header.setResultMessage("조회된 할 일 목록이 없습니다.");
				}
			} else {
				header.setResultCode("01");
				header.setResultMessage("로그인된 사용자 ID가 제공되지 않았습니다.");
			}
		} catch (Exception e) {
			header.setResultCode("99");
			header.setResultMessage("할 일 목록 조회 중 오류가 발생하였습니다.");
			e.printStackTrace(); // 예외를 로그로 출력
		}

		// 최종적으로 응답 설정
		apiResponse.setHeader(header);
		apiResponse.setBody(todoList);

		return apiResponse;
	}

	// 체크리스트 저장
	@ResponseBody
	@PostMapping("/todoList/register")
	public ApiResponse<Integer> todoListRegisterAjax(@RequestBody HashMap<String, String> paramMap) {
		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		Integer todoListId = null;

		try {
			// 할 일 목록 등록
			int result = todoListService.insertTodoList(paramMap);

			if (result > 0) {
				// 등록이 성공하면 ID 조회
				todoListId = todoListService.findTodoListId(paramMap);
				header.setResultCode("00");
				header.setResultMessage("할 일이 성공적으로 등록되었습니다.");
			} else {
				// 등록 실패 처리
				header.setResultCode("02");
				header.setResultMessage("할 일 등록에 실패하였습니다.");
			}
		} catch (Exception e) {
			// 예외 처리
			header.setResultCode("99");
			header.setResultMessage("할 일 등록 중 오류가 발생하였습니다.");
			e.printStackTrace(); // 예외를 로그로 출력
		}

		// 최종적으로 응답 설정
		apiResponse.setHeader(header);
		apiResponse.setBody(todoListId);

		return apiResponse;
	}

	// 체크리스트 상태 변경 처리
	@ResponseBody
	@PostMapping("/todoList/checkedOn")
	public ApiResponse<Integer> checkedOnAjax(@RequestBody Map<String, String> paramMap) {
		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		int result = 0;

		try {
			// 파라미터에서 todoListId와 기타 정보 추출
			if (paramMap == null || paramMap.isEmpty()) {
				header.setResultCode("01");
				header.setResultMessage("파라미터가 제공되지 않았습니다.");
				apiResponse.setHeader(header);
				apiResponse.setBody(result);
				return apiResponse;
			}

			String todoListIdStr = paramMap.get("todoListId");
			String loginUserId = paramMap.get("loginUserId");
			String todoListStatus = paramMap.get("todoListStatus");

			if (todoListIdStr == null || loginUserId == null || todoListStatus == null) {
				header.setResultCode("01");
				header.setResultMessage("필수 파라미터가 누락되었습니다.");
				apiResponse.setHeader(header);
				apiResponse.setBody(result);
				return apiResponse;
			}

			int todoListId = Integer.parseInt(todoListIdStr);

			// 할 일 상태 업데이트
			TodoListUpdate todoListUpdate = new TodoListUpdate(loginUserId, todoListId, todoListStatus);
			result = todoListService.updateTodoListStatus(todoListUpdate);

			// 결과에 따른 응답 설정
			if (result > 0) {
				header.setResultCode("00");
				header.setResultMessage("할 일 상태가 성공적으로 업데이트되었습니다.");
			} else {
				header.setResultCode("02");
				header.setResultMessage("할 일 상태 업데이트에 실패하였습니다.");
			}
		} catch (NumberFormatException e) {
			// 숫자 형식 오류 처리
			header.setResultCode("03");
			header.setResultMessage("할 일 ID가 유효하지 않습니다.");
			e.printStackTrace(); // 예외를 로그로 출력
		} catch (Exception e) {
			// 기타 예외 처리
			header.setResultCode("99");
			header.setResultMessage("할 일 상태 업데이트 중 오류가 발생하였습니다.");
			e.printStackTrace(); // 예외를 로그로 출력
		}

		// 최종적으로 응답 설정
		apiResponse.setHeader(header);
		apiResponse.setBody(result);

		return apiResponse;
	}

	// 체크리스트 삭제 처리
	@ResponseBody
	@PostMapping("/todoList/remove")
	public ApiResponse<Integer> removeTodoListAjax(@RequestBody TodoListRemove todoListRemove) {
		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		int result = 0;

		try {
			// 요청 본문이 null인 경우
			if (todoListRemove == null) {
				header.setResultCode("01");
				header.setResultMessage("요청 본문이 제공되지 않았습니다.");
				apiResponse.setHeader(header);
				apiResponse.setBody(result);
				return apiResponse;
			}

			// 할 일 목록 항목 제거
			result = todoListService.removeTodoListByTodoListId(todoListRemove);

			// 결과에 따른 응답 설정
			if (result > 0) {
				header.setResultCode("00");
				header.setResultMessage("할 일이 성공적으로 제거되었습니다.");
			} else {
				header.setResultCode("02");
				header.setResultMessage("할 일 제거에 실패하였습니다.");
			}
		} catch (Exception e) {
			// 예외 처리
			header.setResultCode("99");
			header.setResultMessage("할 일 제거 중 오류가 발생하였습니다.");
			e.printStackTrace(); // 예외를 로그로 출력
		}

		// 최종적으로 응답 설정
		apiResponse.setHeader(header);
		apiResponse.setBody(result);

		return apiResponse;
	}

}
