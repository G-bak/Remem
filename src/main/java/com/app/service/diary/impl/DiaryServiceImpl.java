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

@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	DiaryDAO diaryDAO;
	
	@Override
	public int insertTalkToBotAllByQuestion(TalkToBotAll talkToBotAll) {
		try {
			return diaryDAO.insertTalkToBotAllByQuestion(talkToBotAll);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int updateTalkToBotAllByChat(TalkToBotAll talkToBotAll) {
		try {
			return diaryDAO.updateTalkToBotAllByChat(talkToBotAll);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<TalkToBotAll> selectTalkToBotAllByUserId(String userId) {
		List<TalkToBotAll> talkToBotAllList = null;
		try {
			return diaryDAO.selectTalkToBotAllByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return talkToBotAllList;
		}
	}
	
	@Override
	public List<List<TalkToBotData>> selectAllTalkToBotData(List<TalkToBotAll> talkToBotAllList) {
		List<List<TalkToBotData>> talkToBotDataList = null;
		try {
			return diaryDAO.selectAllTalkToBotData(talkToBotAllList);
		} catch (Exception e) {
			e.printStackTrace();
			return talkToBotDataList;
		}
	}

	@Override
	public boolean createTalkToBot(TalkToBotAll talkToBotAll) {
		try {
			return diaryDAO.createTalkToBot(talkToBotAll);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int insertUserQuestion(TalkToBotData talkToBotData) {
		try {
			return diaryDAO.insertUserQuestion(talkToBotData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertBotAnswer(TalkToBotData talkToBotData) {
		try {
			return diaryDAO.insertBotAnswer(talkToBotData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public List<ExcludedKeyword> selectExcludedKeywordsByRoomIdMessageIndex(ExcludedKeyword excludedKeyword) {
		List<ExcludedKeyword> keywordList = null;
		try {
			keywordList = diaryDAO.selectExcludedKeywordsByRoomIdMessageIndex(excludedKeyword);
			return keywordList;
        } catch (Exception e) {
            e.printStackTrace();
            return keywordList;
        }
	}
	
	@Override
	public List<UserDiary> selectDiaryListByKeyword(String processData, String userId) {
		List<UserDiary> diaryList = null;
		try {
			diaryList = diaryDAO.selectDiaryListByKeyword(processData, userId);
			
			return diaryList;
        } catch (Exception e) {
            e.printStackTrace();
            
            return diaryList;
        }
	}

	@Override
	public int dropTalkToBot(TalkToBotAll request) {
		try {
			return diaryDAO.dropTalkToBot(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteTalkToBotAll(TalkToBotAll request) {
		try {
			return diaryDAO.deleteTalkToBotAll(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int InsertExcludedKeyword(ExcludedKeyword request) {
		try {
			return diaryDAO.InsertExcludedKeyword(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ExcludedKeyword> selectExcludedKeyword(ExcludedKeyword request) {
		List<ExcludedKeyword> excludedKeywordList = null;
		try {
			return diaryDAO.selectExcludedKeyword(request);
		} catch (Exception e) {
			e.printStackTrace();
			return excludedKeywordList;
		}
	}

	@Override
	public int deleteExcludedKeywords(TalkToBotAll request) {
		try {
			return diaryDAO.deleteExcludedKeywords(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateBotAnswer(TalkToBotData talkToBotData) {
		try {
			return diaryDAO.updateBotAnswer(talkToBotData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteExcludedKeyword(ExcludedKeyword request) {
		try {
			return diaryDAO.deleteExcludedKeyword(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertExcludedKeywordNotMessageIndex(ExcludedKeyword request) {
		try {
			return diaryDAO.insertExcludedKeywordNotMessageIndex(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateQuestionTitle(TalkToBotAll request) {
		try {
			return diaryDAO.updateQuestionTitle(request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
