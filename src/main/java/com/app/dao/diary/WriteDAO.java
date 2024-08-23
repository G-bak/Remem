package com.app.dao.diary;

import java.util.List;

import com.app.dto.diary.UserDiary;

public interface WriteDAO {
	//일기 저장
	public int insertUserDiary(UserDiary diary);

	//일기 조회
	public List<UserDiary> getDiaryListByUserId(String userId);

	//일기 수정
	public int modifyDiary(UserDiary diary);
	
	//일기 삭제
	public int deleteDiary(String diaryId);
}
