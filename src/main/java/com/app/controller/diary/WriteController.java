package com.app.controller.diary;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.dto.diary.UserDiary;
import com.app.service.diary.WriteService;

import java.util.Objects;
import java.util.stream.Stream;

@Controller
public class WriteController {
	@Autowired
	WriteService writeService;
	
	@GetMapping("/diaryWrite")
	public String diaryWrite(HttpSession session) {
	    // 새로운 세션에 데이터를 설정합니다.
		session.setAttribute("userId", "user01");
		
		return "diary/diaryWrite";
	}
	
	@PostMapping("/diarySave")
	public String diarySave(HttpSession session, UserDiary diary) {
		String sessionUserId = (String) session.getAttribute("userId");
		diary.setUserId(sessionUserId);
		System.out.println("User ID: " + diary.getUserId());
		System.out.println("Diary Title: " + diary.getDiaryTitle());
		System.out.println("Write Date: " + diary.getWriteDate());
		System.out.println("Diary Content: " + diary.getDiaryContent());
		
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
		
		System.out.println("Result: " + result);
		
		if (result > 0) {
			return "redirect:/main";
		} else {
			return "redirect:/diaryWrite";
		}
	}
}
