package com.app.dto.todolist;

import lombok.Data;

@Data
public class TodoList {
	int todolistId;
	String userId;
	String todolistContents;
	String todolistStatus;
}
