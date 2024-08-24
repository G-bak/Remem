package com.app.dao.calender;

import java.util.List;
import java.util.Map;

import com.app.dto.calender.Calender;
import com.app.dto.calender.CalenderFriends;
import com.app.dto.calender.CalenderMemoDiary;
import com.app.dto.calender.Friends;


public interface CalenderDAO {
	
	/**
	 * 캘린더 항목을 데이터베이스에 삽입합니다.
	 * 
	 * @param calender 삽입할 캘린더 객체
	 * @return 삽입된 행의 수, 오류 발생 시 0 반환
	 */
	public int insertCalender(Calender calender);
	
	/**
	 * 친구 데이터를 캘린더 항목에 삽입합니다.
	 * 
	 * @param friendData 삽입할 친구 데이터 맵
	 * @return 삽입된 행의 수, 오류 발생 시 0 반환
	 */
	public int insertCalenderFriends(Map<String, String> friendData);
	
	/**
	 * 캘린더 항목을 조회합니다.
	 * 
	 * @param calender 조회 조건을 포함한 캘린더 객체
	 * @return 조회된 캘린더 리스트, 오류 발생 시 null 반환
	 */
	public List<Calender> selectCalender(Calender calender);
	
	/**
	 * 캘린더 항목을 삭제합니다.
	 * 
	 * @param request 삭제할 캘린더 객체
	 * @return 삭제된 행의 수, 오류 발생 시 0 반환
	 */
	public int deleteCalender(Calender request);
	
	/**
	 * 캘린더에 연결된 친구 항목을 삭제합니다.
	 * 
	 * @param request 삭제할 캘린더 객체
	 * @return 삭제된 행의 수, 오류 발생 시 0 반환
	 */
	public int deleteCalenderFriends(Calender request);
	
	/**
	 * 친구 리스트를 조회합니다.
	 * 
	 * @param request 조회 조건을 포함한 친구 객체
	 * @return 조회된 친구 리스트, 오류 발생 시 null 반환
	 */
	public List<Friends> selectFriends(Friends request);
	
	/**
	 * 사용자 ID로 사용자 이름을 조회합니다.
	 * 
	 * @param userId 조회할 사용자 ID
	 * @return 조회된 사용자 이름, 오류 발생 시 null 반환
	 */
	public String selectUserNameByUserId(String userId);
	
	/**
	 * 캘린더에 연결된 친구 리스트를 조회합니다.
	 * 
	 * @param request 조회할 캘린더 친구 객체
	 * @return 조회된 친구 리스트, 오류 발생 시 null 반환
	 */
	public List<CalenderFriends> showFriendList(CalenderFriends request);
	
	/**
	 * 캘린더 세부 정보를 조회합니다.
	 * 
	 * @param request 조회할 캘린더 메모 다이어리 객체
	 * @return 조회된 캘린더 세부 정보, 오류 발생 시 null 반환
	 */
	public CalenderMemoDiary selectCalenderDetail(CalenderMemoDiary request);
	
	/**
	 * 새로운 캘린더 세부 정보를 삽입합니다.
	 * 
	 * @param request 삽입할 캘린더 메모 다이어리 객체
	 * @return 삽입된 행의 수, 오류 발생 시 0 반환
	 */
	public int insertCalenderDetail(CalenderMemoDiary request);
	
	/**
	 * 캘린더 세부 정보를 업데이트합니다.
	 * 
	 * @param request 업데이트할 캘린더 메모 다이어리 객체
	 * @return 업데이트된 행의 수, 오류 발생 시 0 반환
	 */
	public int updateCalenderDetail(CalenderMemoDiary request);
}
