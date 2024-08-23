package com.app.service.todolist;

import java.util.HashMap;
import java.util.List;

import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;

public interface TodoListService {
	// userId로 todolist 조회
	public List<TodoList> findTodoListByLoginUserId(String loginUserId);
	
	// todolist 저장
	public int insertTodoList(HashMap<String, String> paramMap);
	
	// todolist id 찾기
	public int findTodoListId(HashMap<String, String> paramMap);
	
	// todolist 상태 변경
	public int updateTodoListStatus(TodoListUpdate todoListUpdate);
	
	// todolist 삭제
	public int removeTodoListByTodoListId(TodoListRemove todoListRemove);
}