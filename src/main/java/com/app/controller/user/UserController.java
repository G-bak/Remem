package com.app.controller.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dto.user.User;
import com.app.service.UserService;

@Controller
public class UserController {

	// 정규식 패턴
	private static final String ID_REGEX = "^[a-zA-Z0-9]{8,30}$"; // 아이디 : 8~30자의 영문 대소문자, 숫자
	private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,20}$"; // 비밀번호 : 4~20자, 최소 하나의 문자 및 숫자
	private static final String ADDRESS_REGEX = "^.{5,50}$"; // 주소 : 5~50자

	@Autowired
	UserService userService;


	@GetMapping("/startpage")
	public String startpage() {
		return "startpage";
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}	

	//회원가입
	@GetMapping("/user/signup")
	public String signup() {

		System.out.println("test");

		return "signup";
	}

	@PostMapping("/user/signup")
	public String signupAction(Model model, User user) {
		System.out.println(user.getUserName());
		System.out.println(user.getUserAddress());
		System.out.println(user.getUserId());
		System.out.println(user.getUserPassword());


		int result = userService.saveUser(user);
		System.out.println(result);
		
		if (result <= 0) {
			return "signup";
		} 
		
		return "signin";
	}

	//로그인
	@GetMapping("/user/signin")
	public String signin() {

		System.out.println("로그인 요청");

		return "signin";
	}

	@PostMapping("/user/signin")
	public String signinAction(User user, HttpSession session) {

		User loginUser = userService.loginUser(user);

		session.setAttribute("user", loginUser );

		System.out.println("로그인 사용자 정보:");
		System.out.println("ID: " + loginUser.getUserId());
		System.out.println("Name: " + loginUser.getUserName());
		System.out.println("Address: " + loginUser.getUserAddress());
		System.out.println("Password: " + loginUser.getUserPassword());

		System.out.println("로그인 성공 (아이디/이름): " + loginUser.getUserId() + " / " + loginUser.getUserName());

		return "redirect:/main";
	}

	//로그아웃
	@RequestMapping("/user/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/startpage";
	}

	//회원탈퇴
	@RequestMapping("/user/removeUser")
	public String removeUser(User user, HttpSession session) {

		User sessionUser = (User) session.getAttribute("user");

		int result = userService.removeUser(sessionUser.getUserId());

		session.invalidate();

		return "redirect:/startpage";
	}

	//주소변경
	@PostMapping("/user/modifyAddress")
	public String modifyAddress(User user, HttpSession session) {

		User sessionUser = (User) session.getAttribute("user");

		//변경할 주소 가져오기

		System.out.println(user.getUserAddress());

		sessionUser.setUserAddress(user.getUserAddress());

		System.out.println(sessionUser);
		int result = userService.modifyAddress(sessionUser);

		if(result >0) {
			System.out.println("성공");
		}else {
			System.out.println("실패");
		}

		session.setAttribute("user", sessionUser);

		return "redirect:/main" ;
	}

	//비밀번호 변경
	@PostMapping("/user/modifyPassword")
	public String modifyPassword(User user, HttpSession session, @RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword) {	

		User sessionUser = (User) session.getAttribute("user");

		// 세션에서 가져온 사용자 객체가 null인지 확인
		if (sessionUser == null) {
			System.out.println("실패: 세션이 만료되었거나 사용자 정보가 없습니다.");
			return "redirect:/main";
		}

		// 현재 비밀번호와 세션의 비밀번호 비교 && 현재 비밀번호와 새 비밀번호가 동일한지 확인
		if (!sessionUser.getUserPassword().equals(currentPassword)) {
			System.out.println("실패: 현재 비밀번호가 일치하지 않습니다.");
			return "redirect:/main";
		}

		if (currentPassword.equals(newPassword)) {
			System.out.println("실패: 새 비밀번호는 현재 비밀번호와 달라야 합니다.");
			return "redirect:/main";
		}

		// 비밀번호 변경
		sessionUser.setUserPassword(newPassword);
		int result = userService.modifyPassword(sessionUser);

		if (result > 0) {
			System.out.println("성공: 비밀번호가 변경되었습니다.");
		} else {
			System.out.println("실패: 비밀번호 변경에 실패했습니다.");
		}

		session.setAttribute("user", sessionUser);
		return "redirect:/main";
	}
}



