package com.app.service.timecapsule;

import java.util.List;

import com.app.dto.timecapsule.Timecapsule;
import com.app.dto.timecapsule.TimecapsuleSearch;

public interface TimecapsuleService {

	public int saveTimecapsule(Timecapsule tc);
	
	public List<Timecapsule> selectAllTimecapsule();
}