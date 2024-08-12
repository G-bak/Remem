package com.app.dao.todolist;

import java.util.HashMap;
import java.util.List;

import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;

public interface TodoListDAO {
	public List<TodoList> findTodoListByLoginUserId(String loginUserId);
	
	public int insertTodoList(HashMap<String, String> paramMap);
	
	public int findTodoListId(HashMap<String, String> paramMap);
	
	public int updateTodoListStatus(TodoListUpdate todoListUpdate);
	
	public int removeTodoListByTodoListId(TodoListRemove todoListRemove);
}
