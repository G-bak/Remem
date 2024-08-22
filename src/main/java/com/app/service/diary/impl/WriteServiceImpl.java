package com.app.service.diary.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.diary.WriteDAO;
import com.app.dto.diary.UserDiary;
import com.app.service.diary.WriteService;

@Service
public class WriteServiceImpl implements WriteService {

	@Autowired
	WriteDAO writeDAO;
	
	@Override
	public int insertUserDiary(UserDiary diary) {
		try {
			return writeDAO.insertUserDiary(diary);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<UserDiary> getDiaryListByUserId(String userId) {
		 List<UserDiary> getDiaryListByUserId = writeDAO.getDiaryListByUserId(userId);
		 return getDiaryListByUserId;
	}

	@Override
	public int modifyDiary(UserDiary diary) {
		int result = writeDAO.modifyDiary(diary);
		return result;
	}


	@Override
	public int deleteDiary(String diaryId) {
		int result = writeDAO.deleteDiary(diaryId);
		return result;
	}

}
