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
		List<UserDiary> diaryList = null;
		try {
			 return writeDAO.getDiaryListByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return diaryList;
		}
		 
	}
	
	@Override
	public int modifyDiary(UserDiary diary) {
		try {
			int result = writeDAO.modifyDiary(diary);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}


	@Override
	public int deleteDiary(String diaryId) {
		try {
			int result = writeDAO.deleteDiary(diaryId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
