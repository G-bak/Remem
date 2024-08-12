package com.app.dao.diary.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.diary.WriteDAO;
import com.app.dto.diary.UserDiary;

@Repository
public class WriteDAOImpl implements WriteDAO {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int insertUserDiary(UserDiary diary) {
		try {
			return sqlSessionTemplate.insert("diary_mapper.insertUserDiary", diary);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
