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

@Repository
public class DiaryDAOImpl implements DiaryDAO {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertTalkToBotAllByQuestion(TalkToBotAll talkToBotAll) {
		try {
			return sqlSessionTemplate.insert("diary_mapper.insertTalkToBotAllByQuestion", talkToBotAll);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public int updateTalkToBotAllByChat(TalkToBotAll talkToBotAll) {
		try {
			return sqlSessionTemplate.update("diary_mapper.updateTalkToBotAllByChat", talkToBotAll);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<TalkToBotAll> selectTalkToBotAllByUserId(String userId) {
	    List<TalkToBotAll> talkToBotAllList = null;
	    try {
	        talkToBotAllList = sqlSessionTemplate.selectList("diary_mapper.selectTalkToBotAllByUserId", userId);
	        if (talkToBotAllList == null) {
//	            System.out.println("Returned list is null");
	        } else {
//	            System.out.println("Returned list size: " + talkToBotAllList.size());
	            for (TalkToBotAll item : talkToBotAllList) {
	                if (item == null) {
//	                    System.out.println("List contains null item");
	                } else {
//	                    System.out.println("Item: " + item);
	                }
	            }
	        }
	        return talkToBotAllList;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return talkToBotAllList;
	    }
	}
	
	@Override
	public List<List<TalkToBotData>> selectAllTalkToBotData(List<TalkToBotAll> talkToBotAllList) {
	    List<List<TalkToBotData>> talkToBotDataList = new ArrayList<>();

	    for (int i = 0; i < talkToBotAllList.size(); i++) {
	        try {
	            List<TalkToBotData> talkToBotData = sqlSessionTemplate.selectList("diary_mapper.selectAllTalkToBotData", talkToBotAllList.get(i));
	            talkToBotDataList.add(talkToBotData);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return talkToBotDataList;
	}

	@Override
	public boolean createTalkToBot(TalkToBotAll talkToBotAll) {
		try {
			sqlSessionTemplate.update("diary_mapper.createTalkToBot", talkToBotAll);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public int insertUserQuestion(TalkToBotData talkToBotData) {
		try {
			return sqlSessionTemplate.insert("diary_mapper.insertUserQuestion", talkToBotData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertBotAnswer(TalkToBotData talkToBotData) {
		try {
			return sqlSessionTemplate.insert("diary_mapper.insertBotAnswer", talkToBotData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public List<ExcludedKeyword> selectExcludedKeywordsByRoomIdMessageIndex(ExcludedKeyword excludedKeyword) {
		List<ExcludedKeyword> keywordList = null;
		try {
			keywordList = sqlSessionTemplate.selectList("diary_mapper.selectExcludedKeywordsByRoomIdMessageIndex", excludedKeyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return keywordList;
	}
	
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
            // SQL 쿼리를 실행하고 결과를 반환
            return sqlSessionTemplate.selectList("diary_mapper.selectDiaryListByKeyword", params);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        
	}

	@Override
	public int dropTalkToBot(TalkToBotAll request) {
		try {
			return sqlSessionTemplate.update("diary_mapper.dropTalkToBot", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteTalkToBotAll(TalkToBotAll request) {
		try {
			return sqlSessionTemplate.delete("diary_mapper.deleteTalkToBotAll", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int InsertExcludedKeyword(ExcludedKeyword request) {
		try {
			return sqlSessionTemplate.insert("diary_mapper.InsertExcludedKeyword", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ExcludedKeyword> selectExcludedKeyword(ExcludedKeyword request) {
		List<ExcludedKeyword> excludedKeywordList = null;
		try {
			return sqlSessionTemplate.selectList("diary_mapper.selectExcludedKeyword", request);
		} catch (Exception e) {
			e.printStackTrace();
			return excludedKeywordList;
		}
	}

	@Override
	public int deleteExcludedKeywords(TalkToBotAll request) {
		try {
			return sqlSessionTemplate.delete("diary_mapper.deleteExcludedKeywords", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateBotAnswer(TalkToBotData talkToBotData) {
		try {
			return sqlSessionTemplate.update("diary_mapper.updateBotAnswer", talkToBotData);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteExcludedKeyword(ExcludedKeyword request) {
		try {
			return sqlSessionTemplate.delete("diary_mapper.deleteExcludedKeyword", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int insertExcludedKeywordNotMessageIndex(ExcludedKeyword request) {
		try {
			return sqlSessionTemplate.insert("diary_mapper.insertExcludedKeywordNotMessageIndex", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateQuestionTitle(TalkToBotAll request) {
		try {
			return sqlSessionTemplate.update("diary_mapper.updateQuestionTitle", request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
