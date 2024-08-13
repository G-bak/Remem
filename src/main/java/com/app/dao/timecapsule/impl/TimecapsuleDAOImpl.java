package com.app.dao.timecapsule.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.timecapsule.TimecapsuleDAO;
import com.app.dto.timecapsule.Timecapsule;
import com.app.dto.timecapsule.TimecapsuleSearch;

@Repository
public class TimecapsuleDAOImpl implements TimecapsuleDAO{

	@Autowired				
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int saveTimecapsule(Timecapsule tc) {
		// TODO Auto-generated method stub
		int saveTimecapsule = sqlSessionTemplate.insert("timecapsule_mapper.saveTimecapsule", tc);
		return saveTimecapsule;
	}

	@Override
	public Timecapsule selectTimecapsule(TimecapsuleSearch tcs) {
		// TODO Auto-generated method stub
		Timecapsule tc = sqlSessionTemplate.selectOne("timecapsule_mapper.selectTimecapsule", tcs);
		return tc;
	}

	@Override
	public List<Timecapsule> selectAllTimecapsule() {
		// TODO Auto-generated method stub
		List<Timecapsule> tcList = sqlSessionTemplate.selectList("timecapsule_mapper.selectAllTimecapsule");
		
		return tcList;
	}
}
