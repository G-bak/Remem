package com.app.service.todolist.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.todolist.TodoListDAO;
import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;
import com.app.service.todolist.TodoListService;

@Service
public class TodoListServiceImpl implements TodoListService{
	
	@Autowired
	TodoListDAO todoListDAO;
	
	
	@Override
	public List<TodoList> findTodoListByLoginUserId(String loginUserId) {
		List<TodoList> todoList = todoListDAO.findTodoListByLoginUserId(loginUserId);
		return todoList;
	}


	@Override
	public int insertTodoList(HashMap<String, String> paramMap) {
		int result = todoListDAO.insertTodoList(paramMap);
		return result;
	}


	@Override
	public int findTodoListId(HashMap<String, String> paramMap) {
		int todoListId = todoListDAO.findTodoListId(paramMap);
		return todoListId;
	}


	@Override
	public int updateTodoListStatus(TodoListUpdate todoListUpdate) {
		int result = todoListDAO.updateTodoListStatus(todoListUpdate);
		return result;
	}


	@Override
	public int removeTodoListByTodoListId(TodoListRemove todoListRemove) {
		int result = todoListDAO.removeTodoListByTodoListId(todoListRemove);
		return result;
	}
	
	
}
