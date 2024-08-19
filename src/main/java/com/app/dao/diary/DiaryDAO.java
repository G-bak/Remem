package com.app.dao.diary;
import java.util.List;

import com.app.dto.diary.UserDiary;
import com.app.dto.diary.ExcludedKeyword;
import com.app.dto.diary.TalkToBotAll;
import com.app.dto.diary.TalkToBotData;

public interface DiaryDAO {
	public int insertTalkToBotAllByQuestion(TalkToBotAll talkToBotAll);
	
	public int updateTalkToBotAllByChat(TalkToBotAll talkToBotAll);
	
	public List<TalkToBotAll> selectTalkToBotAllByUserId(String userId);
	
	public List<List<TalkToBotData>> selectAllTalkToBotData(List<TalkToBotAll> talkToBotAllList);
	
	public boolean createTalkToBot(TalkToBotAll talkToBotAll);
	
	public int insertUserQuestion(TalkToBotData talkToBotData);
	
	public int insertBotAnswer(TalkToBotData talkToBotData);
	
	public List<ExcludedKeyword> selectExcludedKeywordsByRoomIdMessageIndex(ExcludedKeyword excludedKeyword);
	
	public List<UserDiary> selectDiaryListByKeyword(String processData, String userId);
	
	public int dropTalkToBot(TalkToBotAll request);
	
	public int deleteTalkToBotAll(TalkToBotAll request);
	
	public int InsertExcludedKeyword(ExcludedKeyword request);
	
	public List<ExcludedKeyword> selectExcludedKeyword(ExcludedKeyword request);
	
	public int deleteExcludedKeywords(TalkToBotAll request);
	
	public int updateBotAnswer(TalkToBotData talkToBotData);
	
	public int deleteExcludedKeyword(ExcludedKeyword request);
	
	public int insertExcludedKeywordNotMessageIndex(ExcludedKeyword request);
	
	public int updateQuestionTitle(TalkToBotAll request);
}
