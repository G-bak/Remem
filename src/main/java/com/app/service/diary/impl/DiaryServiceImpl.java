package com.app.service.diary.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.diary.DiaryDAO;
import com.app.dto.diary.UserDiary;
import com.app.dto.diary.ExcludedKeyword;
import com.app.dto.diary.TalkToBotAll;
import com.app.dto.diary.TalkToBotData;
import com.app.service.diary.DiaryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	DiaryDAO diaryDAO;
	
	/**
	 * 새로운 대화 데이터를 질문 기반으로 삽입합니다.
	 * 
	 * @param talkToBotAll 삽입할 대화 데이터
	 * @return 삽입된 행의 수
	 */
	@Override
	public int insertTalkToBotAllByQuestion(TalkToBotAll talkToBotAll) {
		try {
			log.info("질문 기반 대화 데이터 삽입 시도: {}", talkToBotAll);
			return diaryDAO.insertTalkToBotAllByQuestion(talkToBotAll);
		} catch (Exception e) {
			log.error("질문 기반 대화 데이터 삽입 실패: {}", talkToBotAll, e);
			return 0;
		}
	}
	
	/**
	 * 대화 데이터를 채팅 기반으로 업데이트합니다.
	 * 
	 * @param talkToBotAll 업데이트할 대화 데이터
	 * @return 업데이트된 행의 수
	 */
	@Override
	public int updateTalkToBotAllByChat(TalkToBotAll talkToBotAll) {
		try {
			log.info("채팅 기반 대화 데이터 업데이트 시도: {}", talkToBotAll);
			return diaryDAO.updateTalkToBotAllByChat(talkToBotAll);
		} catch (Exception e) {
			log.error("채팅 기반 대화 데이터 업데이트 실패: {}", talkToBotAll, e);
			return 0;
		}
	}

	/**
	 * 사용자 ID를 기반으로 모든 대화 데이터를 조회합니다.
	 * 
	 * @param userId 조회할 사용자 ID
	 * @return 조회된 대화 데이터 리스트
	 */
	@Override
	public List<TalkToBotAll> selectTalkToBotAllByUserId(String userId) {
		List<TalkToBotAll> talkToBotAllList = null;
		try {
			log.info("사용자 ID로 대화 데이터 조회 시도: userId={}", userId);
			return diaryDAO.selectTalkToBotAllByUserId(userId);
		} catch (Exception e) {
			log.error("사용자 ID로 대화 데이터 조회 실패: userId={}", userId, e);
			return talkToBotAllList;
		}
	}
	
	/**
	 * 여러 대화 데이터의 세부 정보를 조회합니다.
	 * 
	 * @param talkToBotAllList 조회할 대화 데이터 리스트
	 * @return 대화 데이터의 세부 정보 리스트
	 */
	@Override
	public List<List<TalkToBotData>> selectAllTalkToBotData(List<TalkToBotAll> talkToBotAllList) {
		List<List<TalkToBotData>> talkToBotDataList = null;
		try {
			log.info("대화 데이터 리스트의 세부 정보 조회 시도");
			return diaryDAO.selectAllTalkToBotData(talkToBotAllList);
		} catch (Exception e) {
			log.error("대화 데이터 리스트의 세부 정보 조회 실패", e);
			return talkToBotDataList;
		}
	}

	/**
	 * 새로운 대화방 테이블을 생성합니다.
	 * 
	 * @param talkToBotAll 생성할 대화방 데이터
	 * @return 생성 성공 여부
	 */
	@Override
	public boolean createTalkToBot(TalkToBotAll talkToBotAll) {
		try {
			log.info("대화방 테이블 생성 시도: {}", talkToBotAll);
			return diaryDAO.createTalkToBot(talkToBotAll);
		} catch (Exception e) {
			log.error("대화방 테이블 생성 실패: {}", talkToBotAll, e);
			return false;
		}
	}

	/**
	 * 사용자의 질문 데이터를 삽입합니다.
	 * 
	 * @param talkToBotData 삽입할 질문 데이터
	 * @return 삽입된 행의 수
	 */
	@Override
	public int insertUserQuestion(TalkToBotData talkToBotData) {
		try {
			log.info("사용자 질문 데이터 삽입 시도: {}", talkToBotData);
			return diaryDAO.insertUserQuestion(talkToBotData);
		} catch (Exception e) {
			log.error("사용자 질문 데이터 삽입 실패: {}", talkToBotData, e);
			return 0;
		}
	}

	/**
	 * Bot의 답변 데이터를 삽입합니다.
	 * 
	 * @param talkToBotData 삽입할 답변 데이터
	 * @return 삽입된 행의 수
	 */
	@Override
	public int insertBotAnswer(TalkToBotData talkToBotData) {
		try {
			log.info("Bot 답변 데이터 삽입 시도: {}", talkToBotData);
			return diaryDAO.insertBotAnswer(talkToBotData);
		} catch (Exception e) {
			log.error("Bot 답변 데이터 삽입 실패: {}", talkToBotData, e);
			return 0;
		}
	}
	
	/**
	 * Room ID와 메시지 인덱스를 기준으로 제외할 키워드를 조회합니다.
	 * 
	 * @param excludedKeyword 조회 조건을 포함한 키워드 객체
	 * @return 조회된 제외 키워드 리스트
	 */
	@Override
	public List<ExcludedKeyword> selectExcludedKeywordsByRoomIdMessageIndex(ExcludedKeyword excludedKeyword) {
		List<ExcludedKeyword> keywordList = null;
		try {
			log.info("제외할 키워드 조회 시도: {}", excludedKeyword);
			keywordList = diaryDAO.selectExcludedKeywordsByRoomIdMessageIndex(excludedKeyword);
			return keywordList;
        } catch (Exception e) {
			log.error("제외할 키워드 조회 실패: {}", excludedKeyword, e);
            return keywordList;
        }
	}
	
	/**
	 * 키워드를 기반으로 다이어리 리스트를 조회합니다.
	 * 
	 * @param processData 조회할 키워드 데이터
	 * @param userId 조회할 사용자 ID
	 * @return 조회된 다이어리 리스트
	 */
	@Override
	public List<UserDiary> selectDiaryListByKeyword(String processData, String userId) {
		List<UserDiary> diaryList = null;
		try {
			log.info("키워드로 다이어리 리스트 조회 시도: keyword={}, userId={}", processData, userId);
			diaryList = diaryDAO.selectDiaryListByKeyword(processData, userId);
			return diaryList;
        } catch (Exception e) {
			log.error("키워드로 다이어리 리스트 조회 실패: keyword={}, userId={}", processData, userId, e);
            return diaryList;
        }
	}

	/**
	 * 대화방 테이블을 삭제합니다.
	 * 
	 * @param request 삭제할 대화방 데이터
	 * @return 삭제된 행의 수
	 */
	@Override
	public int dropTalkToBot(TalkToBotAll request) {
		try {
			log.info("대화방 테이블 삭제 시도: {}", request);
			return diaryDAO.dropTalkToBot(request);
		} catch (Exception e) {
			log.error("대화방 테이블 삭제 실패: {}", request, e);
			return 0;
		}
	}

	/**
	 * 모든 대화 데이터를 삭제합니다.
	 * 
	 * @param request 삭제할 대화 데이터
	 * @return 삭제된 행의 수
	 */
	@Override
	public int deleteTalkToBotAll(TalkToBotAll request) {
		try {
			log.info("모든 대화 데이터 삭제 시도: {}", request);
			return diaryDAO.deleteTalkToBotAll(request);
		} catch (Exception e) {
			log.error("모든 대화 데이터 삭제 실패: {}", request, e);
			return 0;
		}
	}

	/**
	 * 제외할 키워드를 데이터베이스에 추가합니다.
	 * 
	 * @param request 추가할 키워드 데이터
	 * @return 추가된 행의 수
	 */
	@Override
	public int InsertExcludedKeyword(ExcludedKeyword request) {
		try {
			log.info("제외할 키워드 추가 시도: {}", request);
			return diaryDAO.InsertExcludedKeyword(request);
		} catch (Exception e) {
			log.error("제외할 키워드 추가 실패: {}", request, e);
			return 0;
		}
	}

	/**
	 * 제외할 키워드를 조회합니다.
	 * 
	 * @param request 조회할 키워드 데이터
	 * @return 조회된 제외 키워드 리스트
	 */
	@Override
	public List<ExcludedKeyword> selectExcludedKeyword(ExcludedKeyword request) {
		List<ExcludedKeyword> excludedKeywordList = null;
		try {
			log.info("제외할 키워드 조회 시도: {}", request);
			return diaryDAO.selectExcludedKeyword(request);
		} catch (Exception e) {
			log.error("제외할 키워드 조회 실패: {}", request, e);
			return excludedKeywordList;
		}
	}

	/**
	 * 모든 제외 키워드를 삭제합니다.
	 * 
	 * @param request 삭제할 대화방 데이터
	 * @return 삭제된 행의 수
	 */
	@Override
	public int deleteExcludedKeywords(TalkToBotAll request) {
		try {
			log.info("모든 제외 키워드 삭제 시도: {}", request);
			return diaryDAO.deleteExcludedKeywords(request);
		} catch (Exception e) {
			log.error("모든 제외 키워드 삭제 실패: {}", request, e);
			return 0;
		}
	}

	/**
	 * Bot의 답변 데이터를 업데이트합니다.
	 * 
	 * @param talkToBotData 업데이트할 답변 데이터
	 * @return 업데이트된 행의 수
	 */
	@Override
	public int updateBotAnswer(TalkToBotData talkToBotData) {
		try {
			log.info("Bot 답변 데이터 업데이트 시도: {}", talkToBotData);
			return diaryDAO.updateBotAnswer(talkToBotData);
		} catch (Exception e) {
			log.error("Bot 답변 데이터 업데이트 실패: {}", talkToBotData, e);
			return 0;
		}
	}

	/**
	 * 특정 키워드를 삭제합니다.
	 * 
	 * @param request 삭제할 키워드 데이터
	 * @return 삭제된 행의 수
	 */
	@Override
	public int deleteExcludedKeyword(ExcludedKeyword request) {
		try {
			log.info("특정 키워드 삭제 시도: {}", request);
			return diaryDAO.deleteExcludedKeyword(request);
		} catch (Exception e) {
			log.error("특정 키워드 삭제 실패: {}", request, e);
			return 0;
		}
	}

	/**
	 * 메시지 인덱스를 제외한 키워드를 삽입합니다.
	 * 
	 * @param request 삽입할 키워드 데이터
	 * @return 삽입된 행의 수
	 */
	@Override
	public int insertExcludedKeywordNotMessageIndex(ExcludedKeyword request) {
		try {
			log.info("메시지 인덱스 제외 키워드 삽입 시도: {}", request);
			return diaryDAO.insertExcludedKeywordNotMessageIndex(request);
		} catch (Exception e) {
			log.error("메시지 인덱스 제외 키워드 삽입 실패: {}", request, e);
			return 0;
		}
	}

	/**
	 * Bot과의 질문 제목을 수정합니다.
	 * 
	 * @param request 수정할 질문 제목 데이터
	 * @return 수정된 행의 수
	 */
	@Override
	public int updateQuestionTitle(TalkToBotAll request) {
		try {
			log.info("Bot 질문 제목 수정 시도: {}", request);
			return diaryDAO.updateQuestionTitle(request);
		} catch (Exception e) {
			log.error("Bot 질문 제목 수정 실패: {}", request, e);
			return 0;
		}
	}
}
