package com.app.controller.diary;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.dto.diary.UserDiary;
import com.app.service.diary.WriteService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WriteController {
	@Autowired
	WriteService writeService;
	
	@GetMapping("/diaryWrite")
	public String diaryWrite(HttpSession session) {
	    // 새로운 세션에 데이터를 설정합니다.
		session.setAttribute("userId", "user1");
		
		return "diary/diaryWrite";
	}
	
	@PostMapping("/diarySave")
	public String diarySave(HttpSession session, UserDiary diary) {
		String sessionUserId = (String) session.getAttribute("userId");
		diary.setUserId(sessionUserId);
		// System.out.println("User ID: " + diary.getUserId());
		// System.out.println("Diary Title: " + diary.getDiaryTitle());
		// System.out.println("Write Date: " + diary.getWriteDate());
		// System.out.println("Diary Content: " + diary.getDiaryContent());
		
		String userId = diary.getUserId();
		String title = diary.getDiaryTitle();
		String date = diary.getWriteDate();
		String content = diary.getDiaryContent();
		int result = 0;

		boolean allPresent = Stream.of(userId, title, date, content).allMatch(Objects::nonNull);

		if (allPresent) {
		    result = writeService.insertUserDiary(diary);
		} else {
			System.out.println("DB 전송 실패, 파라미터가 NULL 값인지 확인하세요");
		}
		
		// System.out.println("Result: " + result);
		
		if (result > 0) {
			return "redirect:/main";
		} else {
			return "redirect:/diaryWrite";
		}
	}
	

	
	@PostMapping("/modifyDiary")
	public String modifyDiary(HttpSession session, UserDiary diary) throws ParseException {
	    
		// 폼에서 전달된 diaryId를 그대로 사용
		
	    // System.out.println(diary.getDiaryId());
	    // System.out.println(diary.getDiaryTitle());
	    // System.out.println(diary.getDiaryContent());
	    // System.out.println(diary.getWriteDate());


	    // 일기 수정 작업 수행
	    int result = writeService.modifyDiary(diary);

	    if (result > 0) {
	        System.out.println("일기 수정 성공");
	    } else {
	        System.out.println("일기 수정 실패");
	    }

	    // 세션에 수정된 일기 저장
	    session.setAttribute("diary", diary);

	    return "redirect:/main";
	}
	
	@RequestMapping("/deleteDiary")
	public String deleteDiary(HttpSession session, @RequestParam("diaryId") String diaryId) throws ParseException {
	    
		// URL 쿼리 파라미터로 전송된 diaryId 값을 받아서 사용
	    
	    // 일기 삭제 작업 수행
	    int result = writeService.deleteDiary(diaryId);

	    if (result > 0) {
	        System.out.println("일기 삭제 성공");
	    } else {
	        System.out.println("일기 삭제 실패");
	    }

	    return "redirect:/main";
	}
	
}
