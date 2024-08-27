package com.app.controller.diary;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.dto.diary.UserDiary;
import com.app.dto.user.User;
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

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WriteController {

	@Autowired
	WriteService writeService;

	@GetMapping("/diaryWrite")
	public String diaryWrite(HttpSession session) {
		log.info("diaryWrite 호출됨 - 세션 ID: {}", session.getId());
		return "diary/diaryWrite";
	}

	@PostMapping("/diarySave")
	public String diarySave(HttpSession session, UserDiary diary) {
		log.info("diarySave 호출됨 - 세션 ID: {}, 일기 제목: {}", session.getId(), diary.getDiaryTitle());

		try {
			// 사용자 ID 설정
			User sessionUser = (User) session.getAttribute("user");
			if (sessionUser == null) {
				log.warn("세션에 사용자 정보가 없음");
				return "redirect:/diaryWrite";
			}

			// 변경할 주소 가져오기
			String sessionUserId = sessionUser.getUserId().toString();
			session.setAttribute("userId", sessionUserId);
			diary.setUserId(sessionUserId);

			String userId = diary.getUserId();
			String title = diary.getDiaryTitle();
			String date = diary.getWriteDate();
			String content = diary.getDiaryContent();
			int result = 0;

			boolean allPresent = Stream.of(userId, title, date, content).allMatch(Objects::nonNull);

			if (allPresent) {
				result = writeService.insertUserDiary(diary);
				log.info("일기 저장 성공 - 사용자 ID: {}", userId);
			} else {
				log.warn("DB 전송 실패, 파라미터에 NULL 값이 있음 - 사용자 ID: {}, 제목: {}, 날짜: {}, 내용: {}", userId, title, date,
						content);
			}

			if (result > 0) {
				return "redirect:/main";
			} else {
				return "redirect:/diaryWrite";
			}
		} catch (Exception e) {
			log.error("일기 저장 중 오류 발생", e);
			return "redirect:/diaryWrite";
		}
	}

	@PostMapping("/modifyDiary")
	public String modifyDiary(HttpSession session, UserDiary diary) throws ParseException {
		log.info("modifyDiary 호출됨 - 세션 ID: {}, 일기 ID: {}", session.getId(), diary.getDiaryId());

		try {
			// 일기 수정 작업 수행
			int result = writeService.modifyDiary(diary);

			if (result > 0) {
				log.info("일기 수정 성공 - 일기 ID: {}", diary.getDiaryId());
			} else {
				log.warn("일기 수정 실패 - 일기 ID: {}", diary.getDiaryId());
			}

			// 세션에 수정된 일기 저장
			session.setAttribute("diary", diary);

			return "redirect:/main";
		} catch (Exception e) {
			log.error("일기 수정 중 오류 발생", e);
			return "redirect:/diaryWrite";
		}
	}

	@RequestMapping("/deleteDiary")
	public String deleteDiary(HttpSession session, @RequestParam("diaryId") String diaryId) {
		log.info("deleteDiary 호출됨 - 세션 ID: {}, 일기 ID: {}", session.getId(), diaryId);

		try {
			// 일기 삭제 작업 수행
			int result = writeService.deleteDiary(diaryId);

			if (result > 0) {
				log.info("일기 삭제 성공 - 일기 ID: {}", diaryId);
			} else {
				log.warn("일기 삭제 실패 - 일기 ID: {}", diaryId);
			}

			return "redirect:/main";
		} catch (Exception e) {
			log.error("일기 삭제 중 오류 발생", e);
			return "redirect:/main";
		}
	}
}
