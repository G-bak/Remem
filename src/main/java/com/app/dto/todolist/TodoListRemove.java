package com.app.dto.todolist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListRemove {
	int todoListId;
	String loginUserId;
}
