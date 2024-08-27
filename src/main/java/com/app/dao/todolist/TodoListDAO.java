package com.app.dao.todolist;

import java.util.HashMap;
import java.util.List;

import com.app.dto.todolist.TodoList;
import com.app.dto.todolist.TodoListRemove;
import com.app.dto.todolist.TodoListUpdate;

/**
 * TodoListDAO 인터페이스는 할 일 목록과 관련된 데이터 액세스 메서드를 정의합니다.
 * 할 일 목록 조회, 추가, 수정, 삭제 등의 기능을 제공합니다.
 */
public interface TodoListDAO {

    /**
     * 특정 사용자의 할 일 목록을 조회합니다.
     *
     * @param loginUserId 로그인한 사용자의 ID
     * @return 조회된 할 일 목록
     */
    public List<TodoList> findTodoListByLoginUserId(String loginUserId);

    /**
     * 새로운 할 일 목록을 저장합니다.
     *
     * @param paramMap 할 일 목록 정보를 포함한 매개변수 맵
     * @return 성공적으로 저장된 경우 1, 그렇지 않으면 0
     */
    public int insertTodoList(HashMap<String, String> paramMap);

    /**
     * 특정 조건에 따라 할 일 목록 ID를 찾습니다.
     *
     * @param paramMap 조건을 포함한 매개변수 맵
     * @return 조회된 할 일 목록 ID
     */
    public int findTodoListId(HashMap<String, String> paramMap);

    /**
     * 할 일 목록의 상태를 업데이트합니다.
     *
     * @param todoListUpdate 상태 업데이트 정보를 포함한 객체
     * @return 성공적으로 업데이트된 경우 1, 그렇지 않으면 0
     */
    public int updateTodoListStatus(TodoListUpdate todoListUpdate);

    /**
     * 할 일 목록 ID로 할 일 목록을 삭제합니다.
     *
     * @param todoListRemove 삭제할 할 일 목록 정보를 포함한 객체
     * @return 성공적으로 삭제된 경우 1, 그렇지 않으면 0
     */
    public int removeTodoListByTodoListId(TodoListRemove todoListRemove);
}
