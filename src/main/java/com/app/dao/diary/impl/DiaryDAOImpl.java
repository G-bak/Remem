package com.app.dao.diary.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.diary.DiaryDAO;
import com.app.dto.diary.UserDiary;
import com.app.dto.diary.ExcludedKeyword;
import com.app.dto.diary.TalkToBotAll;
import com.app.dto.diary.TalkToBotData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DiaryDAOImpl implements DiaryDAO {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 질문 기반으로 새로운 대화 데이터를 삽입합니다.
	 * 
	 * @param talkToBotAll 삽입할 대화 데이터
	 * @return 삽입된 행의 수
	 */
	@Override
	public int insertTalkToBotAllByQuestion(TalkToBotAll talkToBotAll) {
		try {
			log.info("질문 기반 대화 데이터 삽입 시도: {}", talkToBotAll);
			return sqlSessionTemplate.insert("diary_mapper.insertTalkToBotAllByQuestion", talkToBotAll);
		} catch (Exception e) {
			log.error("질문 기반 대화 데이터 삽입 실패: {}", talkToBotAll, e);
			return 0;
		}
	}

	/**
	 * 채팅 기반으로 대화 데이터를 업데이트합니다.
	 * 
	 * @param talkToBotAll 업데이트할 대화 데이터
	 * @return 업데이트된 행의 수
	 */
	@Override
	public int updateTalkToBotAllByChat(TalkToBotAll talkToBotAll) {
		try {
			log.info("채팅 기반 대화 데이터 업데이트 시도: {}", talkToBotAll);
			return sqlSessionTemplate.update("diary_mapper.updateTalkToBotAllByChat", talkToBotAll);
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
	        talkToBotAllList = sqlSessionTemplate.selectList("diary_mapper.selectTalkToBotAllByUserId", userId);
	        if (talkToBotAllList == null) {
	            log.info("조회된 대화 데이터 리스트가 null입니다.");
	        } else {
	            log.info("조회된 대화 데이터 리스트 크기: {}", talkToBotAllList.size());
	            for (TalkToBotAll item : talkToBotAllList) {
	                if (item == null) {
	                    log.info("리스트에 null 항목이 포함되어 있습니다.");
	                } else {
	                    log.info("리스트 항목: {}", item);
	                }
	            }
	        }
	        return talkToBotAllList;
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
	    List<List<TalkToBotData>> talkToBotDataList = new ArrayList<>();

	    for (int i = 0; i < talkToBotAllList.size(); i++) {
	        try {
	            log.info("대화 데이터 세부 정보 조회 시도: {}", talkToBotAllList.get(i));
	            List<TalkToBotData> talkToBotData = sqlSessionTemplate.selectList("diary_mapper.selectAllTalkToBotData", talkToBotAllList.get(i));
	            talkToBotDataList.add(talkToBotData);
	        } catch (Exception e) {
	            log.error("대화 데이터 세부 정보 조회 실패: {}", talkToBotAllList.get(i), e);
	        }
	    }

	    return talkToBotDataList;
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
			sqlSessionTemplate.update("diary_mapper.createTalkToBot", talkToBotAll);
            return true;
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
			return sqlSessionTemplate.insert("diary_mapper.insertUserQuestion", talkToBotData);
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
			return sqlSessionTemplate.insert("diary_mapper.insertBotAnswer", talkToBotData);
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
			keywordList = sqlSessionTemplate.selectList("diary_mapper.selectExcludedKeywordsByRoomIdMessageIndex", excludedKeyword);
		} catch (Exception e) {
			log.error("제외할 키워드 조회 실패: {}", excludedKeyword, e);
		}
		
		return keywordList;
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
        // 띄어쓰기를 기준으로 문자열을 분할
        String[] keywordsArray = processData.split("\\s+");

        // 배열을 리스트로 변환
        List<String> keywordsList = new ArrayList<>(Arrays.asList(keywordsArray));

        // 파라미터를 담을 맵 생성
	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("keywordsList", keywordsList);
        
        try {
            log.info("키워드로 다이어리 리스트 조회 시도: keyword={}, userId={}", processData, userId);
            // SQL 쿼리를 실행하고 결과를 반환
            return sqlSessionTemplate.selectList("diary_mapper.selectDiaryListByKeyword", params);
        } catch (Exception e) {
            log.error("키워드로 다이어리 리스트 조회 실패: keyword={}, userId={}", processData, userId, e);
            return new ArrayList<>();
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
			return sqlSessionTemplate.update("diary_mapper.dropTalkToBot", request);
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
			return sqlSessionTemplate.delete("diary_mapper.deleteTalkToBotAll", request);
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
			return sqlSessionTemplate.insert("diary_mapper.InsertExcludedKeyword", request);
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
			return sqlSessionTemplate.selectList("diary_mapper.selectExcludedKeyword", request);
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
			return sqlSessionTemplate.delete("diary_mapper.deleteExcludedKeywords", request);
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
			return sqlSessionTemplate.update("diary_mapper.updateBotAnswer", talkToBotData);
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
			return sqlSessionTemplate.delete("diary_mapper.deleteExcludedKeyword", request);
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
			return sqlSessionTemplate.insert("diary_mapper.insertExcludedKeywordNotMessageIndex", request);
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
			return sqlSessionTemplate.update("diary_mapper.updateQuestionTitle", request);
		} catch (Exception e) {
			log.error("Bot 질문 제목 수정 실패: {}", request, e);
			return 0;
		}
	}

}
