package com.app.service.calender.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.calender.CalenderDAO;
import com.app.dto.calender.Calender;
import com.app.dto.calender.CalenderFriends;
import com.app.dto.calender.CalenderMemoDiary;
import com.app.dto.calender.Friends;
import com.app.service.calender.CalenderService;

@Service
public class CalenderServiceImpl implements CalenderService {

	@Autowired
	CalenderDAO calenderDAO;
	
	@Override
	public int insertCalender(Calender calender) {
		try {
			return calenderDAO.insertCalender(calender);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertCalenderFriends(Map<String, String> friendData) {
		try {
			return calenderDAO.insertCalenderFriends(friendData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Calender> selectCalender(Calender calender) {
		List<Calender> calenderList = null;
		try {
			return calenderDAO.selectCalender(calender);
		} catch (Exception e) {
			e.printStackTrace();
			return calenderList;
		}
	}

	@Override
	public int deleteCalender(Calender request) {
		try {
			return calenderDAO.deleteCalender(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int deleteCalenderFriends(Calender request) {
		try {
			return calenderDAO.deleteCalenderFriends(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Friends> selectFriends(Friends request) {
		List<Friends> friendList = null;
		try {
			return calenderDAO.selectFriends(request);
		} catch (Exception e) {
			e.printStackTrace();
			return friendList;
		}
	}

	@Override
	public String selectUserNameByUserId(String userId) {
		try {
			return calenderDAO.selectUserNameByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CalenderFriends> showFriendList(CalenderFriends request) {
		List<CalenderFriends> friendList = null;
		try {
			return calenderDAO.showFriendList(request);
		} catch (Exception e) {
			e.printStackTrace();
			return friendList;
		}
	}

	@Override
	public CalenderMemoDiary selectCalenderDetail(CalenderMemoDiary request) {
		CalenderMemoDiary calenderDetail = null;
		try {
			return calenderDAO.selectCalenderDetail(request);
		} catch (Exception e) {
			e.printStackTrace();
			return calenderDetail;
		}
	}

	@Override
	public int insertCalenderDetail(CalenderMemoDiary request) {
		try {
			return calenderDAO.insertCalenderDetail(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateCalenderDetail(CalenderMemoDiary request) {
		try {
			// System.out.println("calenderService.updateCalenderDetail 호출됨");
			return calenderDAO.updateCalenderDetail(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
