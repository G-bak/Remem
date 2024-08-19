package com.app.service.calender;

import java.util.List;
import java.util.Map;

import com.app.dto.calender.Calender;
import com.app.dto.calender.CalenderFriends;
import com.app.dto.calender.CalenderMemoDiary;
import com.app.dto.calender.Friends;

public interface CalenderService {
	public int insertCalender(Calender calender);
	
	public int insertCalenderFriends(Map<String, String> friendData);
	
	public List<Calender> selectCalender(Calender calender);
	
	public int deleteCalender(Calender request);
	
	public int deleteCalenderFriends(Calender request);
	
	public List<Friends> selectFriends(Friends request);
	
	public String selectUserNameByUserId(String userId);
	
	public List<CalenderFriends> showFriendList(CalenderFriends request);
	
	public CalenderMemoDiary selectCalenderDetail(CalenderMemoDiary request);
	
	public int insertCalenderDetail(CalenderMemoDiary request);
	
	public int updateCalenderDetail(CalenderMemoDiary request);
}

