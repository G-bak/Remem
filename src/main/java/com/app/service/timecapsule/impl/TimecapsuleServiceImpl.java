package com.app.service.timecapsule.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.timecapsule.TimecapsuleDAO;
import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;
import com.app.dto.timecapsule.Timecapsule;
import com.app.dto.timecapsule.TimecapsuleSearch;
import com.app.service.timecapsule.TimecapsuleService;

@Service
public class TimecapsuleServiceImpl implements TimecapsuleService{

	@Autowired
	TimecapsuleDAO timecapsuleDAO;

	@Override
	public int saveTimecapsule(Timecapsule tc) {
		// TODO Auto-generated method stub
		int saveTimecapsule = timecapsuleDAO.saveTimecapsule(tc);
		return saveTimecapsule;
	}

	@Override
	public Timecapsule selectTimecapsule(TimecapsuleSearch tcs) {
		// TODO Auto-generated method stub
		Timecapsule tc = timecapsuleDAO.selectTimecapsule(tcs);
		return tc;
	}

	@Override
	public List<Timecapsule> selectAllTimecapsule() {
		// TODO Auto-generated method stub
		
		List<Timecapsule> tcList = timecapsuleDAO.selectAllTimecapsule();
		return tcList;
	}
	
}