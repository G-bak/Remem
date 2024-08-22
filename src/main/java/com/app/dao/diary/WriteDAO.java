package com.app.dao.diary;

import java.util.List;

import com.app.dto.diary.UserDiary;

public interface WriteDAO {
	public int insertUserDiary(UserDiary diary);

	public List<UserDiary> getDiaryListByUserId(String userId);

	public int modifyDiary(UserDiary diary);

	public int deleteDiary(String diaryId);
}
