package com.app.service.diary.impl;

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

}
