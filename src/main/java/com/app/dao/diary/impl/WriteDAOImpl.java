package com.app.dao.diary.impl;

import java.util.List;

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

	@Override
	public List<UserDiary> getDiaryListByUserId(String userId) {
		List<UserDiary> getDiaryListByUserId = sqlSessionTemplate.selectList("diary_mapper.getDiaryListByUserId", userId);
		return getDiaryListByUserId;
	}

	@Override
	public int modifyDiary(UserDiary diary) {
	    int result = sqlSessionTemplate.update("diary_mapper.modifyDiary", diary);
	    return result;
	}

	@Override
	public int deleteDiary(String diaryId) {
		int result = sqlSessionTemplate.delete("diary_mapper.deleteDiary", diaryId);
	    return result;
	}

}
