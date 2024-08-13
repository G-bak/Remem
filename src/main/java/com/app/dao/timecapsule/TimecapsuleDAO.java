package com.app.dao.timecapsule;

import java.util.List;

import com.app.dto.timecapsule.Timecapsule;
import com.app.dto.timecapsule.TimecapsuleSearch;

public interface TimecapsuleDAO {

	public Timecapsule selectTimecapsule(TimecapsuleSearch tcs);
	
	public int saveTimecapsule(Timecapsule tc);
	
	public List<Timecapsule> selectAllTimecapsule();
}