package com.app.controller.user;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.user.RequestUserDTO;
import com.app.dto.user.User;
import com.app.service.file.FileService;
import com.app.service.user.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	FileService fileService;

	// 정규식 패턴
	private static final String verifyId = "^[a-zA-Z0-9]{4,20}$"; // 4~20글자, 영어 및 숫자만 가능
	private static final String verifyName = "^[a-zA-Z가-힣]{2,15}$"; // 2~15글자, 한글 및 영어만 가능
	private static final String verifyPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,20}$"; // 최소 1개의 문자와 숫자, 4~20글자
	private static final String verifyAddress = "^.{5,50}$"; // 5~50글자, 모든 문자 허용

	@GetMapping("/startpage")
	public String startpage() {
		return "startpage";
	}

	@GetMapping("/main")
	public String main() {
		System.out.println("들어옴");
		return "main";
	}

	// 회원가입
	@GetMapping("/user/signup")
	public String signup() {

		return "signup";
	}

	@PostMapping("/user/signup")
	public String signupAction(Model model, User user) {
		try {
			// 유효성 검사
			validateUserInput(user);

			int result = userService.saveUser(user);
			if (result <= 0) {
				throw new Exception("Sign up failed.");

			}

			return "redirect:/signin";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "signup";
		}
	}

	// 유효성 검사 메서드
	// validateUserInput 메서드는 사용자의 입력을 검증하고, 입력이 올바르지 않으면 예외를 발생시킴.
	private void validateUserInput(User user) throws Exception {
		if (!Pattern.matches(verifyId, user.getUserId())) { // user.getUserId() 값이 verifyId를 만족하지 않으면 예외처리
			throw new Exception("아이디 형식이 올바르지 않습니다. 4-20자의 영문자와 숫자만 사용 가능합니다.");
		}

		if (!Pattern.matches(verifyName, user.getUserName())) { // user.getUserName() 값이 verifyName를 만족하지 않으면 예외처리
			throw new Exception("이름 형식이 올바르지 않습니다. 2-15자의 한글 또는 영문자만 사용 가능합니다.");
		}

		if (!Pattern.matches(verifyPassword, user.getUserPassword())) { // user.getUserPassword() 값이 verifyPassword를
																		// 만족하지 않으면 예외처리
			throw new Exception("비밀번호는 4-20자이어야 하며, 최소 하나의 문자와 숫자를 포함해야 합니다.");
		}

		if (!Pattern.matches(verifyAddress, user.getUserAddress())) { // user.getUserAddress() 값이 verifyAddress를 만족하지
																		// 않으면 예외처리
			throw new Exception("주소는 5-50자 사이여야 합니다.");
		}
	}

	// 로그인
	@GetMapping("/user/signin")
	public String signin(HttpSession session) {
		System.out.println(session.getAttribute("user"));
		return "signin";
	}

	@PostMapping("/user/signin")
	public String signinAction(User user, HttpSession session, Model model) {
		try {
			User loginUser = userService.loginUser(user);
			if (loginUser == null) {
				throw new Exception("아이디 또는 비밀번호가 올바르지 않습니다.");
			}

			session.setAttribute("user", loginUser);

			// 프로필 사진 세선 처리
			String loginUserFilePath = fileService.findFilePathByUserId(loginUser.getUserId());
			session.setAttribute("filePath", loginUserFilePath);

			System.out.println(session.getAttribute("user") + "정상");

			return "redirect:/main";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println(session.getAttribute("user") + "오류");
			return "signin";
		}
	}

	// 로그아웃
	@RequestMapping("/user/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/startpage";
	}

	// 회원 탈퇴
	@RequestMapping("/user/removeUser")
	public String removeUser(HttpSession session, Model model) {
		try {
			User sessionUser = (User) session.getAttribute("user");
			if (sessionUser == null) {
				throw new Exception("세션에서 사용자를 찾을 수 없습니다.");
			}

			int result = userService.removeUser(sessionUser.getUserId());
			if (result <= 0) {
				throw new Exception("회원 탈퇴에 실패했습니다.");
			}

			session.invalidate();
			return "redirect:/startpage";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "main";
		}
	}

	// 비밀번호 변경
	@PostMapping("/user/modifyPassword")
	public String modifyPassword(HttpSession session, @RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword, Model model) {
		try {

			User sessionUser = (User) session.getAttribute("user");
			if (sessionUser == null) {
				throw new Exception("세션에서 사용자를 찾을 수 없습니다.");
			}

			// 현재 비밀번호가 일치하는지 확인
			if (!sessionUser.getUserPassword().equals(currentPassword)) {
				throw new Exception("현재 비밀번호가 일치하지 않습니다.");
			}

			// 새로운 비밀번호가 현재 비밀번호와 동일하지 않은지 확인
			if (currentPassword.equals(newPassword)) {
				throw new Exception("새 비밀번호는 현재 비밀번호와 달라야 합니다.");
			}

			// 새로운 비밀번호의 유효성 검사
			if (!Pattern.matches(verifyPassword, newPassword)) {
				throw new Exception("새 비밀번호는 4-20자이어야 하며, 최소 하나의 문자와 숫자를 포함해야 합니다.");
			}

			sessionUser.setUserPassword(newPassword);
			int result = userService.modifyPassword(sessionUser);
			if (result <= 0) {
				throw new Exception("비밀번호 변경에 실패했습니다.");
			}

			session.setAttribute("user", sessionUser);
			return "redirect:/main";

		} catch (Exception e) {

			model.addAttribute("errorMessage", e.getMessage());
			return "main";

		}
	}

	// 아이디 중복체크 ajax
	@ResponseBody
	@PostMapping("/checkIdDuplicated")
	public int checkDuplicatedId(@RequestBody Map<String, String> requestData) {
		String signupId = requestData.get("signupId");
		
		System.out.println(signupId);

		int result = userService.checkDuplicatedId(signupId);
		
		
		return result;
	}

	@GetMapping("/timer")
	public String timer() {
		return "timer";
	}

}
