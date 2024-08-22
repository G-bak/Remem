package com.app.dao.calender.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dao.calender.CalenderDAO;
import com.app.dto.calender.Calender;
import com.app.dto.calender.CalenderFriends;
import com.app.dto.calender.CalenderMemoDiary;
import com.app.dto.calender.Friends;

@Repository
public class CalenderDAOImpl implements CalenderDAO {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertCalender(Calender calender) {
		try {
			return sqlSessionTemplate.insert("calender_mapper.insertCalender", calender);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertCalenderFriends(Map<String, String> friendData) {
		try {
			return sqlSessionTemplate.insert("calender_mapper.insertCalenderFriends", friendData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Calender> selectCalender(Calender calender) {
		List<Calender> calenderList = null;
		try {
			return sqlSessionTemplate.selectList("calender_mapper.selectCalender", calender);
		} catch (Exception e) {
			e.printStackTrace();
			return calenderList;
		}
	}

	@Override
	public int deleteCalender(Calender request) {
		try {
			return sqlSessionTemplate.delete("calender_mapper.deleteCalender", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int deleteCalenderFriends(Calender request) {
		try {
			return sqlSessionTemplate.delete("calender_mapper.deleteCalenderFriends", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Friends> selectFriends(Friends request) {
		List<Friends> friendList = null;
		try {
			return sqlSessionTemplate.selectList("calender_mapper.selectFriends", request);
		} catch (Exception e) {
			e.printStackTrace();
			return friendList;
		}
	}

	@Override
	public String selectUserNameByUserId(String userId) {
		try {
			return sqlSessionTemplate.selectOne("calender_mapper.selectUserNameByUserId", userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CalenderFriends> showFriendList(CalenderFriends request) {
		List<CalenderFriends> friendList = null;
		try {
			return sqlSessionTemplate.selectList("calender_mapper.showFriendList", request);
		} catch (Exception e) {
			e.printStackTrace();
			return friendList;
		}
	}

	@Override
	public CalenderMemoDiary selectCalenderDetail(CalenderMemoDiary request) {
		CalenderMemoDiary calenderDetail = null;
		try {
			return sqlSessionTemplate.selectOne("calender_mapper.selectCalenderDetail", request);
		} catch (Exception e) {
			e.printStackTrace();
			return calenderDetail;
		}
	}

	@Override
	public int insertCalenderDetail(CalenderMemoDiary request) {
		try {
			return sqlSessionTemplate.insert("calender_mapper.insertCalenderDetail", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int updateCalenderDetail(CalenderMemoDiary request) {
		try {
			// System.out.println("calenderService.updateCalenderDetail 호출됨");
			return sqlSessionTemplate.update("calender_mapper.updateCalenderDetail", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
