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

import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;
import com.app.service.todolist.TodoListService;

@Controller
public class TodoListController {
	
	@Autowired
	TodoListService todoListService;
	
	//전체 체크리스트 조회
	@ResponseBody
	@PostMapping("/todoList/viewAll")
	public List<TodoList> todoListAjax(@RequestParam String loginUserId){
		List<TodoList> todoList = todoListService.findTodoListByLoginUserId(loginUserId);
		return todoList;
	}
	
	//체크리스트 저장
	@ResponseBody
	@PostMapping("/todoList/register")
	public int todoListRegisterAjax(@RequestBody HashMap<String, String> paramMap) {
		
		int result = todoListService.insertTodoList(paramMap);
		int todoListId = 0;
		
		//todoList의 id 값을 반환
		if(result > 0){
			todoListId = todoListService.findTodoListId(paramMap);
		}
		return todoListId;
	}
	
	//체크리스트 상태 변경 처리
	@ResponseBody
	@PostMapping("/todoList/checkedOn")
	public int checkedOnAjax(@RequestBody Map<String, String> paramMap) {
			
        int todoListId = Integer.parseInt(paramMap.get("todoListId"));
        
        TodoListUpdate todoListUpdate = new TodoListUpdate(paramMap.get("loginUserId"), todoListId, paramMap.get("todoListStatus"));
		
        int result = todoListService.updateTodoListStatus(todoListUpdate);
		return result;
	}
	
	//체크리스트 삭제 처리
	@PostMapping("todoList/remove")
	@ResponseBody
	public int removeTodoListAjax(@RequestBody TodoListRemove todoListRemove) {
		
		int result = todoListService.removeTodoListByTodoListId(todoListRemove);
		return result;
	}
	
	
}
