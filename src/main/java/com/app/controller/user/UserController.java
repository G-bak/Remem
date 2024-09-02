package com.app.controller.user;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.dto.diary.UserDiary;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.user.User;
import com.app.service.file.FileService;
import com.app.service.diary.WriteService;
import com.app.service.friend.FriendService;
import com.app.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // Lombok을 사용한 Logger 자동 생성
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	FileService fileService;

	@Autowired
	WriteService writeService;

	@Autowired
	FriendService friendService;

	// 정규식 패턴
	private static final String verifyId = "^[a-zA-Z0-9]{4,20}$"; // 4~20글자, 영어 및 숫자만 가능
	private static final String verifyName = "^[a-zA-Z가-힣]{2,15}$"; // 2~15글자, 한글 및 영어만 가능
	private static final String verifyPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,20}$"; // 최소 1개의 문자와 숫자, 4~20글자
	private static final String verifyAddress = "^.{5,50}$"; // 5~50글자, 모든 문자 허용

	@GetMapping("/startpage")
	public String startpage() {
		log.info("startpage 호출됨");
		return "user/startpage";
	}

	@GetMapping("/main")
	public String main(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page) {
		try {
			log.info("main 페이지 호출됨 - 세션 ID: {}, 페이지: {}", session.getId(), page);

			// 사용자 ID 설정
			User sessionUser = (User) session.getAttribute("user");

			if (sessionUser == null) {
				throw new Exception("세션에서 사용자를 찾을 수 없습니다.");
			}

			// 변경할 주소 가져오기
			String userId = sessionUser.getUserId().toString();
			session.setAttribute("userId", userId);

			// 다이어리 목록 가져오기
			List<UserDiary> userDiaryList = writeService.getDiaryListByUserId(userId);
			session.setAttribute("userDiaryList", userDiaryList);

			// 페이지네이션을 위한 로직
			int pageSize = 8; // 페이지당 글 개수
			int totalCount = userDiaryList.size();
			int totalPages = (int) Math.ceil((double) totalCount / pageSize);

			int startIndex = (page - 1) * pageSize;
			int endIndex = Math.min(startIndex + pageSize, totalCount);
			List<UserDiary> pageDiaryList = userDiaryList.subList(startIndex, endIndex);

			// 모델에 필요한 데이터 추가
			model.addAttribute("userDiaryList", pageDiaryList);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("currentPage", page);

			// 친구 목록을 가져옴
			FriendStatusDTO friendStatusDTO = new FriendStatusDTO();
			friendStatusDTO.setLoginUserId(userId);

			// 팔로잉 팔로워
			int followerCount = friendService.countFollower(userId);
			int followingCount = friendService.countFollowing(userId);

			model.addAttribute("follower", followerCount);
			model.addAttribute("following", followingCount);
			model.addAttribute("userId", userId);

			log.info("메인 페이지 로드 성공 - 사용자 ID: {}", userId);

		} catch (Exception e) {
			log.error("메인 페이지 로드 중 오류 발생", e);
			model.addAttribute("errorMessage", "메인 페이지를 불러오는 중 오류가 발생했습니다.");
			return "user/startpage";
		}

		return "user/main";
	}

	// 회원가입
	@GetMapping("/user/signup")
	public String signup(Model model) {
		log.info("signup 페이지 호출됨");

		// 만약 사용자 입력값이 모델에 없다면 빈 User 객체를 모델에 추가
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new User());
		}

		return "user/signup";
	}

	@PostMapping("/user/signup")
	public String signupAction(Model model, User user) {
		try {
			log.info("회원가입 요청 처리 - 사용자 ID: {}", user.getUserId());

			// 유효성 검사
			validateUserInput(user);

			int result = userService.saveUser(user);
			if (result <= 0) {
				throw new Exception("회원가입에 실패했습니다.");
			}

			log.info("회원가입 성공 - 사용자 ID: {}", user.getUserId());
			return "redirect:/user/signin";
		} catch (Exception e) {
			log.error("회원가입 중 오류 발생", e);

			// 예외 발생 시 입력값과 에러 메시지를 모델에 추가
			model.addAttribute("user", user);
			model.addAttribute("errorMessage", e.getMessage());

			// 예외 메시지에 따라 특정 필드를 비움
			if (e.getMessage().contains("비밀번호")) {
				user.setUserPassword(null); // 비밀번호 필드를 비움
			} else if (e.getMessage().contains("이름")) {
				user.setUserName(null); // 이름 필드를 비움
			} else if (e.getMessage().contains("아이디")) {
				user.setUserId(null); // 아이디 필드를 비움
			}

			return "user/signup";
		}
	}

	// 유효성 검사 메서드
	private void validateUserInput(User user) throws Exception {
		if (!Pattern.matches(verifyId, user.getUserId())) {
			log.warn("아이디 형식이 올바르지 않음 - 사용자 ID: {}", user.getUserId());	
		}

		if (!Pattern.matches(verifyName, user.getUserName())) {
			log.warn("이름 형식이 올바르지 않음 - 사용자 이름: {}", user.getUserName());
		}

		if (!Pattern.matches(verifyPassword, user.getUserPassword())) {
			log.warn("비밀번호 형식이 올바르지 않음 - 사용자 ID: {}", user.getUserId());
		}

		if (!Pattern.matches(verifyAddress, user.getUserAddress())) {
			log.warn("주소 형식이 올바르지 않음 - 사용자 ID: {}", user.getUserId());
		}
	}

	// 로그인
	@GetMapping("/user/signin")
	public String signin(HttpSession session) {
		log.info("signin 페이지 호출됨 - 세션 ID: {}", session.getId());
		return "user/signin";
	}

	@PostMapping("/user/signin")
	public String signinAction(User user, HttpSession session, Model model) {
		try {
			log.info("로그인 요청 처리 - 사용자 ID: {}", user.getUserId());

			User loginUser = userService.loginUser(user);
			if (loginUser == null) {
				throw new Exception("아이디 또는 비밀번호가 올바르지 않습니다.");
			}

			session.setAttribute("user", loginUser);

			// 프로필 사진 url 세션 처리
			String loginUserFilePath = fileService.findFilePathByUserId(loginUser.getUserId());
			session.setAttribute("filePath", loginUserFilePath);

			log.info("로그인 성공 - 사용자 ID: {}", user.getUserId());
			return "redirect:/main";
		} catch (Exception e) {
			log.error("로그인 중 오류 발생", e);
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/signin";
		}
	}

	// 로그아웃
	@RequestMapping("/user/logout")
	public String logout(HttpSession session) {
		log.info("로그아웃 요청 처리 - 세션 ID: {}", session.getId());
		session.invalidate();
		return "redirect:/startpage";
	}

	// 회원 탈퇴
	@RequestMapping("/user/removeUser")
	public String removeUser(HttpSession session, Model model) {
		try {
			log.info("회원 탈퇴 요청 처리 - 세션 ID: {}", session.getId());

			User sessionUser = (User) session.getAttribute("user");
			if (sessionUser == null) {
				throw new Exception("세션에서 사용자를 찾을 수 없습니다.");
			}

			int result = userService.removeUser(sessionUser.getUserId());
			if (result <= 0) {
				throw new Exception("회원 탈퇴에 실패했습니다.");
			}

			session.invalidate();
			log.info("회원 탈퇴 성공 - 사용자 ID: {}", sessionUser.getUserId());
			return "redirect:/startpage";
		} catch (Exception e) {
			log.error("회원 탈퇴 중 오류 발생", e);
			model.addAttribute("errorMessage", e.getMessage());
			return "user/main";
		}
	}

	// 주소변경
	@PostMapping("/user/modifyAddress")
	public String modifyAddress(User user, HttpSession session) {
		try {
			log.info("주소 변경 요청 처리 - 사용자 ID: {}", user.getUserId());

			User sessionUser = (User) session.getAttribute("user");

			// 변경할 주소 가져오기
			sessionUser.setUserAddress(user.getUserAddress());

			int result = userService.modifyAddress(sessionUser);
			if (result > 0) {
				log.info("주소 변경 성공 - 사용자 ID: {}", sessionUser.getUserId());
			} else {
				log.warn("주소 변경 실패 - 사용자 ID: {}", sessionUser.getUserId());
			}

			session.setAttribute("user", sessionUser);
			return "redirect:/main";
		} catch (Exception e) {
			log.error("주소 변경 중 오류 발생", e);
			return "redirect:/main";
		}
	}

	// 비밀번호 변경
	@PostMapping("/user/modifyPassword")
	public String modifyPassword(HttpSession session, @RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword, Model model, RedirectAttributes redirectAttributes) {
		try {
			log.info("비밀번호 변경 요청 처리 - 세션 ID: {}", session.getId());

			User sessionUser = (User) session.getAttribute("user");
			if (sessionUser == null) {
				redirectAttributes.addFlashAttribute("pwErrorMessage", "세션에서 사용자를 찾을 수 없습니다.");
				return "redirect:/main";
			}

			// 현재 비밀번호가 일치하는지 확인
			if (!sessionUser.getUserPassword().equals(currentPassword)) {
				redirectAttributes.addFlashAttribute("pwErrorMessage", "현재 비밀번호와 일치하지 않습니다.");
				return "redirect:/main";
			}

			// 새로운 비밀번호가 현재 비밀번호와 동일하지 않은지 확인
			if (currentPassword.equals(newPassword)) {
				redirectAttributes.addFlashAttribute("pwErrorMessage", "새 비밀번호는 현재 비밀번호와 달라야 합니다.");
				return "redirect:/main";
			}

			// 새로운 비밀번호의 유효성 검사
			if (!Pattern.matches(verifyPassword, newPassword)) {
				redirectAttributes.addFlashAttribute("pwErrorMessage", "새 비밀번호는 4-20자이어야 하며, 최소 하나의 문자와 숫자를 포함해야 합니다.");
				return "redirect:/main";
			}

			sessionUser.setUserPassword(newPassword);
			int result = userService.modifyPassword(sessionUser);
			
			if (result <= 0) {
				redirectAttributes.addFlashAttribute("pwErrorMessage", "비밀번호 변경에 실패했습니다.");
				return "redirect:/main";
			}

			session.setAttribute("user", sessionUser);
			log.info("비밀번호 변경 성공 - 사용자 ID: {}", sessionUser.getUserId());
			redirectAttributes.addFlashAttribute("pwErrorMessage", "비밀번호 변경 성공했습니다.");
			return "redirect:/main";
		} catch (Exception e) {
			log.error("비밀번호 변경 중 오류 발생", e);
			redirectAttributes.addFlashAttribute("pwErrorMessage", "비밀번호 변경 중 오류 발생");
			return "redirect:/main";
		}
	}

	// 아이디 중복체크 ajax
	@ResponseBody
	@PostMapping("/user/checkIdDuplicated")
	public ApiResponse<Integer> checkDuplicatedId(@RequestBody Map<String, String> requestData) {
		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		int result = 0;

		try {
			log.info("아이디 중복 체크 요청 처리 - 요청 데이터: {}", requestData);

			// 요청 데이터와 signupId의 유효성 검사
			if (requestData != null && !requestData.isEmpty()) {
				String signupId = requestData.get("signupId");

				if (signupId != null && !signupId.isEmpty()) {
					// 아이디 중복 체크
					result = userService.checkDuplicatedId(signupId);
				} else {
					// signupId가 비어 있는 경우
					log.warn("아이디가 입력되지 않음");
					header.setResultCode("01");
					header.setResultMessage("아이디가 입력되지 않았습니다.");
					apiResponse.setHeader(header);
					apiResponse.setBody(result);
					return apiResponse;
				}
			} else {
				// requestData가 null이거나 비어 있는 경우
				log.warn("잘못된 요청 데이터");
				header.setResultCode("01");
				header.setResultMessage("잘못된 요청입니다.");
				apiResponse.setHeader(header);
				apiResponse.setBody(result);
				return apiResponse;
			}

			// 결과에 따른 응답 설정
			if (result == 0) {
				header.setResultCode("00");
				header.setResultMessage("중복체크가 완료되었습니다. 사용가능");
				log.info("아이디 중복 체크 완료 - 사용 가능");
			} else {
				header.setResultCode("02");
				header.setResultMessage("중복된 아이디가 존재합니다.");
				log.warn("아이디 중복 - 사용 불가");
			}
		} catch (Exception e) {
			// 예외 처리
			log.error("아이디 중복 체크 중 오류 발생", e);
			header.setResultCode("99");
			header.setResultMessage("서버 오류가 발생했습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(result);

		return apiResponse;
	}

}
