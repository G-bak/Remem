package com.app.service.diary;

import java.util.List;

import com.app.dto.diary.UserDiary;

public interface WriteService {
	public int insertUserDiary(UserDiary diary);

	public List<UserDiary> getDiaryListByUserId(String userId);

	public int modifyDiary(UserDiary diary);

	public int deleteDiary(String diaryId);

	
}
