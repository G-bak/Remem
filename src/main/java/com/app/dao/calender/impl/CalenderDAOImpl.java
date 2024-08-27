package com.app.dao.calender.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.calender.CalenderDAO;
import com.app.dto.calender.Calender;
import com.app.dto.calender.CalenderFriends;
import com.app.dto.calender.CalenderMemoDiary;
import com.app.dto.calender.Friends;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CalenderDAOImpl implements CalenderDAO {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 캘린더 항목을 데이터베이스에 삽입합니다.
	 * 
	 * @param calender 삽입할 캘린더 객체
	 * @return 삽입된 행의 수, 오류 발생 시 0 반환
	 */
	@Override
	public int insertCalender(Calender calender) {
		try {
			log.info("캘린더 삽입 중: {}", calender);
			return sqlSessionTemplate.insert("calender_mapper.insertCalender", calender);
		} catch (Exception e) {
			log.error("캘린더 삽입 오류: {}", calender, e);
			return 0;
		}
	}

	/**
	 * 친구 데이터를 캘린더 항목에 삽입합니다.
	 * 
	 * @param friendData 삽입할 친구 데이터 맵
	 * @return 삽입된 행의 수, 오류 발생 시 0 반환
	 */
	@Override
	public int insertCalenderFriends(Map<String, String> friendData) {
		try {
			log.info("캘린더 친구 삽입 중: {}", friendData);
			return sqlSessionTemplate.insert("calender_mapper.insertCalenderFriends", friendData);
		} catch (Exception e) {
			log.error("캘린더 친구 삽입 오류: {}", friendData, e);
			return 0;
		}
	}

	/**
	 * 캘린더 항목을 조회합니다.
	 * 
	 * @param calender 조회 조건을 포함한 캘린더 객체
	 * @return 조회된 캘린더 리스트, 오류 발생 시 null 반환
	 */
	@Override
	public List<Calender> selectCalender(Calender calender) {
		List<Calender> calenderList = null;
		try {
			log.info("캘린더 조회 중: {}", calender);
			return sqlSessionTemplate.selectList("calender_mapper.selectCalender", calender);
		} catch (Exception e) {
			log.error("캘린더 조회 오류: {}", calender, e);
			return calenderList;
		}
	}

	/**
	 * 캘린더 항목을 삭제합니다.
	 * 
	 * @param request 삭제할 캘린더 객체
	 * @return 삭제된 행의 수, 오류 발생 시 0 반환
	 */
	@Override
	public int deleteCalender(Calender request) {
		try {
			log.info("캘린더 삭제 중: {}", request);
			return sqlSessionTemplate.delete("calender_mapper.deleteCalender", request);
		} catch (Exception e) {
			log.error("캘린더 삭제 오류: {}", request, e);
			return 0;
		}
	}
	
	/**
	 * 캘린더에 연결된 친구 항목을 삭제합니다.
	 * 
	 * @param request 삭제할 캘린더 객체
	 * @return 삭제된 행의 수, 오류 발생 시 0 반환
	 */
	@Override
	public int deleteCalenderFriends(Calender request) {
		try {
			log.info("캘린더 친구 삭제 중: {}", request);
			return sqlSessionTemplate.delete("calender_mapper.deleteCalenderFriends", request);
		} catch (Exception e) {
			log.error("캘린더 친구 삭제 오류: {}", request, e);
			return 0;
		}
	}

	/**
	 * 친구 리스트를 조회합니다.
	 * 
	 * @param request 조회 조건을 포함한 친구 객체
	 * @return 조회된 친구 리스트, 오류 발생 시 null 반환
	 */
	@Override
	public List<Friends> selectFriends(Friends request) {
		List<Friends> friendList = null;
		try {
			log.info("친구 리스트 조회 중: {}", request);
			return sqlSessionTemplate.selectList("calender_mapper.selectFriends", request);
		} catch (Exception e) {
			log.error("친구 리스트 조회 오류: {}", request, e);
			return friendList;
		}
	}

	/**
	 * 사용자 ID로 사용자 이름을 조회합니다.
	 * 
	 * @param userId 조회할 사용자 ID
	 * @return 조회된 사용자 이름, 오류 발생 시 null 반환
	 */
	@Override
	public String selectUserNameByUserId(String userId) {
		try {
			log.info("사용자 ID로 사용자 이름 조회 중: {}", userId);
			return sqlSessionTemplate.selectOne("calender_mapper.selectUserNameByUserId", userId);
		} catch (Exception e) {
			log.error("사용자 ID로 사용자 이름 조회 오류: {}", userId, e);
			return null;
		}
	}

	/**
	 * 캘린더에 연결된 친구 리스트를 조회합니다.
	 * 
	 * @param request 조회할 캘린더 친구 객체
	 * @return 조회된 친구 리스트, 오류 발생 시 null 반환
	 */
	@Override
	public List<CalenderFriends> showFriendList(CalenderFriends request) {
		List<CalenderFriends> friendList = null;
		try {
			log.info("캘린더 친구 리스트 조회 중: {}", request);
			return sqlSessionTemplate.selectList("calender_mapper.showFriendList", request);
		} catch (Exception e) {
			log.error("캘린더 친구 리스트 조회 오류: {}", request, e);
			return friendList;
		}
	}

	/**
	 * 캘린더 세부 정보를 조회합니다.
	 * 
	 * @param request 조회할 캘린더 메모 다이어리 객체
	 * @return 조회된 캘린더 세부 정보, 오류 발생 시 null 반환
	 */
	@Override
	public CalenderMemoDiary selectCalenderDetail(CalenderMemoDiary request) {
		CalenderMemoDiary calenderDetail = null;
		try {
			log.info("캘린더 세부 정보 조회 중: {}", request);
			return sqlSessionTemplate.selectOne("calender_mapper.selectCalenderDetail", request);
		} catch (Exception e) {
			log.error("캘린더 세부 정보 조회 오류: {}", request, e);
			return calenderDetail;
		}
	}

	/**
	 * 새로운 캘린더 세부 정보를 삽입합니다.
	 * 
	 * @param request 삽입할 캘린더 메모 다이어리 객체
	 * @return 삽입된 행의 수, 오류 발생 시 0 반환
	 */
	@Override
	public int insertCalenderDetail(CalenderMemoDiary request) {
		try {
			log.info("캘린더 세부 정보 삽입 중: {}", request);
			return sqlSessionTemplate.insert("calender_mapper.insertCalenderDetail", request);
		} catch (Exception e) {
			log.error("캘린더 세부 정보 삽입 오류: {}", request, e);
			return 0;
		}
	}
	
	/**
	 * 캘린더 세부 정보를 업데이트합니다.
	 * 
	 * @param request 업데이트할 캘린더 메모 다이어리 객체
	 * @return 업데이트된 행의 수, 오류 발생 시 0 반환
	 */
	@Override
	public int updateCalenderDetail(CalenderMemoDiary request) {
		try {
			log.info("캘린더 세부 정보 업데이트 중: {}", request);
			return sqlSessionTemplate.update("calender_mapper.updateCalenderDetail", request);
		} catch (Exception e) {
			log.error("캘린더 세부 정보 업데이트 오류: {}", request, e);
			return 0;
		}
	}
}
