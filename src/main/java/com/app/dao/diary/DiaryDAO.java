package com.app.dao.diary;

import java.util.List;

import com.app.dto.diary.UserDiary;
import com.app.dto.diary.ExcludedKeyword;
import com.app.dto.diary.TalkToBotAll;
import com.app.dto.diary.TalkToBotData;

public interface DiaryDAO {

    /**
     * 새로운 대화 데이터를 질문 기반으로 삽입합니다.
     * 
     * @param talkToBotAll 삽입할 대화 데이터
     * @return 삽입된 행의 수
     */
    public int insertTalkToBotAllByQuestion(TalkToBotAll talkToBotAll);
    
    /**
     * 대화 데이터를 채팅 기반으로 업데이트합니다.
     * 
     * @param talkToBotAll 업데이트할 대화 데이터
     * @return 업데이트된 행의 수
     */
    public int updateTalkToBotAllByChat(TalkToBotAll talkToBotAll);
    
    /**
     * 사용자 ID를 기반으로 모든 대화 데이터를 조회합니다.
     * 
     * @param userId 조회할 사용자 ID
     * @return 조회된 대화 데이터 리스트
     */
    public List<TalkToBotAll> selectTalkToBotAllByUserId(String userId);
    
    /**
     * 여러 대화 데이터의 세부 정보를 조회합니다.
     * 
     * @param talkToBotAllList 조회할 대화 데이터 리스트
     * @return 대화 데이터의 세부 정보 리스트
     */
    public List<List<TalkToBotData>> selectAllTalkToBotData(List<TalkToBotAll> talkToBotAllList);
    
    /**
     * 새로운 대화방 테이블을 생성합니다.
     * 
     * @param talkToBotAll 생성할 대화방 데이터
     * @return 생성 성공 여부
     */
    public boolean createTalkToBot(TalkToBotAll talkToBotAll);
    
    /**
     * 사용자의 질문 데이터를 삽입합니다.
     * 
     * @param talkToBotData 삽입할 질문 데이터
     * @return 삽입된 행의 수
     */
    public int insertUserQuestion(TalkToBotData talkToBotData);
    
    /**
     * Bot의 답변 데이터를 삽입합니다.
     * 
     * @param talkToBotData 삽입할 답변 데이터
     * @return 삽입된 행의 수
     */
    public int insertBotAnswer(TalkToBotData talkToBotData);
    
    /**
     * Room ID와 메시지 인덱스를 기준으로 제외할 키워드를 조회합니다.
     * 
     * @param excludedKeyword 조회 조건을 포함한 키워드 객체
     * @return 조회된 제외 키워드 리스트
     */
    public List<ExcludedKeyword> selectExcludedKeywordsByRoomIdMessageIndex(ExcludedKeyword excludedKeyword);
    
    /**
     * 키워드를 기반으로 다이어리 리스트를 조회합니다.
     * 
     * @param processData 조회할 키워드 데이터
     * @param userId 조회할 사용자 ID
     * @return 조회된 다이어리 리스트
     */
    public List<UserDiary> selectDiaryListByKeyword(String processData, String userId);
    
    /**
     * 대화방 테이블을 삭제합니다.
     * 
     * @param request 삭제할 대화방 데이터
     * @return 삭제된 행의 수
     */
    public int dropTalkToBot(TalkToBotAll request);
    
    /**
     * 모든 대화 데이터를 삭제합니다.
     * 
     * @param request 삭제할 대화 데이터
     * @return 삭제된 행의 수
     */
    public int deleteTalkToBotAll(TalkToBotAll request);
    
    /**
     * 제외할 키워드를 데이터베이스에 추가합니다.
     * 
     * @param request 추가할 키워드 데이터
     * @return 추가된 행의 수
     */
    public int InsertExcludedKeyword(ExcludedKeyword request);
    
    /**
     * 제외할 키워드를 조회합니다.
     * 
     * @param request 조회할 키워드 데이터
     * @return 조회된 제외 키워드 리스트
     */
    public List<ExcludedKeyword> selectExcludedKeyword(ExcludedKeyword request);
    
    /**
     * 모든 제외 키워드를 삭제합니다.
     * 
     * @param request 삭제할 대화방 데이터
     * @return 삭제된 행의 수
     */
    public int deleteExcludedKeywords(TalkToBotAll request);
    
    /**
     * Bot의 답변 데이터를 업데이트합니다.
     * 
     * @param talkToBotData 업데이트할 답변 데이터
     * @return 업데이트된 행의 수
     */
    public int updateBotAnswer(TalkToBotData talkToBotData);
    
    /**
     * 특정 키워드를 삭제합니다.
     * 
     * @param request 삭제할 키워드 데이터
     * @return 삭제된 행의 수
     */
    public int deleteExcludedKeyword(ExcludedKeyword request);
    
    /**
     * 메시지 인덱스를 제외한 키워드를 삽입합니다.
     * 
     * @param request 삽입할 키워드 데이터
     * @return 삽입된 행의 수
     */
    public int insertExcludedKeywordNotMessageIndex(ExcludedKeyword request);
    
    /**
     * Bot과의 질문 제목을 수정합니다.
     * 
     * @param request 수정할 질문 제목 데이터
     * @return 수정된 행의 수
     */
    public int updateQuestionTitle(TalkToBotAll request);
}

