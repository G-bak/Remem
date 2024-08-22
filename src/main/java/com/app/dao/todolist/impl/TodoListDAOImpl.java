package com.app.dao.todolist.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.todolist.TodoListDAO;
import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;

@Repository
public class TodoListDAOImpl implements TodoListDAO{
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<TodoList> findTodoListByLoginUserId(String loginUserId) {
		List<TodoList> todoList = sqlSessionTemplate.selectList("todoList_mapper.findTodoListByLoginUserId", loginUserId);
		return todoList;
	}

	@Override
	public int insertTodoList(HashMap<String, String> paramMap) {
		
		
		int result = sqlSessionTemplate.insert("todoList_mapper.insertTodoList", paramMap);
		
		return result;
	}

	@Override
	public int findTodoListId(HashMap<String, String> paramMap) {
		int todoListId = sqlSessionTemplate.selectOne("todoList_mapper.findTodoListId", paramMap);
		return todoListId;
	}

	@Override
	public int updateTodoListStatus(TodoListUpdate todoListUpdate) {
		int result = sqlSessionTemplate.update("todoList_mapper.updateTodoListStatus", todoListUpdate);
		return result;
	}

	@Override
	public int removeTodoListByTodoListId(TodoListRemove todoListRemove) {
		int result = sqlSessionTemplate.delete("todoList_mapper.removeTodoListByTodoListId", todoListRemove);
		return result;
	}

}
