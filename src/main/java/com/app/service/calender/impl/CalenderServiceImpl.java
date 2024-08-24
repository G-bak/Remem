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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalenderServiceImpl implements CalenderService {

    @Autowired
    CalenderDAO calenderDAO;

    /**
     * 새로운 캘린더 항목을 데이터베이스에 삽입합니다.
     * 
     * @param calender 삽입할 캘린더 객체
     * @return 삽입된 행의 수, 오류 발생 시 0 반환
     */
    @Override
    public int insertCalender(Calender calender) {
        try {
            // log.info("캘린더 삽입 중: {}", calender);
            return calenderDAO.insertCalender(calender);
        } catch (Exception e) {
            log.error("캘린더 삽입 오류: {}", calender, e);
            return 0;
        }
    }

    /**
     * 친구를 캘린더 항목에 연결하여 데이터베이스에 삽입합니다.
     * 
     * @param friendData 친구와 캘린더 ID를 포함한 맵
     * @return 삽입된 행의 수, 오류 발생 시 0 반환
     */
    @Override
    public int insertCalenderFriends(Map<String, String> friendData) {
        try {
            // log.info("캘린더 친구 삽입 중: {}", friendData);
            return calenderDAO.insertCalenderFriends(friendData);
        } catch (Exception e) {
            log.error("캘린더 친구 삽입 오류: {}", friendData, e);
            return 0;
        }
    }

    /**
     * 제공된 조건에 따라 캘린더 항목을 조회합니다.
     * 
     * @param calender 조회 조건을 포함한 캘린더 객체
     * @return 조회된 캘린더 항목 리스트, 오류 발생 시 null 반환
     */
    @Override
    public List<Calender> selectCalender(Calender calender) {
        try {
            // log.info("캘린더 조회 중: {}", calender);
            return calenderDAO.selectCalender(calender);
        } catch (Exception e) {
            log.error("캘린더 조회 오류: {}", calender, e);
            return null;
        }
    }

    /**
     * 데이터베이스에서 캘린더 항목을 삭제합니다.
     * 
     * @param request 삭제할 캘린더 객체
     * @return 삭제된 행의 수, 오류 발생 시 0 반환
     */
    @Override
    public int deleteCalender(Calender request) {
        try {
            // log.info("캘린더 삭제 중: {}", request);
            return calenderDAO.deleteCalender(request);
        } catch (Exception e) {
            log.error("캘린더 삭제 오류: {}", request, e);
            return 0;
        }
    }

    /**
     * 캘린더 항목에서 친구와의 연관을 삭제합니다.
     * 
     * @param request 친구와 연관된 캘린더 객체
     * @return 삭제된 행의 수, 오류 발생 시 0 반환
     */
    @Override
    public int deleteCalenderFriends(Calender request) {
        try {
            // log.info("캘린더 친구 삭제 중: {}", request);
            return calenderDAO.deleteCalenderFriends(request);
        } catch (Exception e) {
            log.error("캘린더 친구 삭제 오류: {}", request, e);
            return 0;
        }
    }

    /**
     * 제공된 조건에 따라 친구 리스트를 조회합니다.
     * 
     * @param request 조회 조건을 포함한 친구 객체
     * @return 조회된 친구 리스트, 오류 발생 시 null 반환
     */
    @Override
    public List<Friends> selectFriends(Friends request) {
        try {
            // log.info("친구 리스트 조회 중: {}", request);
            return calenderDAO.selectFriends(request);
        } catch (Exception e) {
            log.error("친구 리스트 조회 오류: {}", request, e);
            return null;
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
            // log.info("사용자 ID로 사용자 이름 조회 중: {}", userId);
            return calenderDAO.selectUserNameByUserId(userId);
        } catch (Exception e) {
            log.error("사용자 ID로 사용자 이름 조회 오류: {}", userId, e);
            return null;
        }
    }

    /**
     * 캘린더의 친구 리스트를 조회합니다.
     * 
     * @param request 조회 조건을 포함한 캘린더 친구 객체
     * @return 조회된 친구 리스트, 오류 발생 시 null 반환
     */
    @Override
    public List<CalenderFriends> showFriendList(CalenderFriends request) {
        try {
            // log.info("캘린더 친구 리스트 조회 중: {}", request);
            return calenderDAO.showFriendList(request);
        } catch (Exception e) {
            log.error("캘린더 친구 리스트 조회 오류: {}", request, e);
            return null;
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
        try {
            // log.info("캘린더 세부 정보 조회 중: {}", request);
            return calenderDAO.selectCalenderDetail(request);
        } catch (Exception e) {
            log.error("캘린더 세부 정보 조회 오류: {}", request, e);
            return null;
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
            // log.info("캘린더 세부 정보 삽입 중: {}", request);
            return calenderDAO.insertCalenderDetail(request);
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
            // log.info("캘린더 세부 정보 업데이트 중: {}", request);
            return calenderDAO.updateCalenderDetail(request);
        } catch (Exception e) {
            log.error("캘린더 세부 정보 업데이트 오류: {}", request, e);
            return 0;
        }
    }
}
