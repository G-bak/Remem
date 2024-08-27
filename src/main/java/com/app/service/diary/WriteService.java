package com.app.service.diary;

import java.util.List;

import com.app.dto.diary.UserDiary;

public interface WriteService {
	
	/**
	 * 일기 작성.
	 *
	 * @param diary 추가할 일기 정보를 담고 있는 UserDiary 객체.
	 * @return 일기 작성이 성공적으로 이루어지면 1 , 그렇지 않으면 0. 
	 */
	public int insertUserDiary(UserDiary diary);

	/**
	 * 사용자 ID로 해당 사용자의 일기 목록을 불러옴.
	 *
	 * @param userId 일기를 조회할 사용자의 ID.
	 * @return 사용자의 일기 목록을 담고 있는 UserDiary 객체의 리스트.
	 */
	public List<UserDiary> getDiaryListByUserId(String userId);

	/**
	 * 일기 수정.
	 *
	 * @param diary 수정할 일기 정보를 담고 있는 UserDiary 객체.
	 * @return 일기 수정이 성공적으로 이루어지면 1 , 그렇지 않으면 0. 
	 */
	public int modifyDiary(UserDiary diary);

	/**
	 * 일기 삭제.
	 *
	 * @param diaryId 삭제할 일기의 ID.
	 * @return 일기 삭제가 성공적으로 이루어지면 1 , 그렇지 않으면 0. 
	 */
	public int deleteDiary(String diaryId);

	
}
