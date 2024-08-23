package com.app.dao.timecapsule;

import java.util.List;

import com.app.dto.timecapsule.Timecapsule;
import com.app.dto.timecapsule.TimecapsuleSearch;

public interface TimecapsuleDAO {
	//타임캡슐 저장
	public int saveTimecapsule(Timecapsule tc);
	
	//타임캡슐 전체 조회
	public List<Timecapsule> selectAllTimecapsule();
}