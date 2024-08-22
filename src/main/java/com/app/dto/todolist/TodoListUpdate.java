package com.app.dto.todolist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoListUpdate {
	String loginUserId;
	int todoListId;
	String todoListStatus;
}
