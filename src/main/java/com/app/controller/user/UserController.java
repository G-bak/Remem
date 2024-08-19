package com.app.controller.user;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dto.diary.UserDiary;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.user.User;
import com.app.service.diary.WriteService;
import com.app.service.friend.FriendService;
import com.app.service.user.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService; 
    
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
        return "startpage";
    }
    
    
    @GetMapping("/main")
    public String main(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page) {
    	System.out.println("test");
        // 사용자 ID 설정
        session.setAttribute("userId", "user1"); 
        String userId = session.getAttribute("userId").toString();
        
        // 다이어리 목록 가져오기
        List<UserDiary> userDiaryList = null;
        if (userId != null && !userId.isEmpty()) {
            userDiaryList = writeService.getDiaryListByUserId(userId);
            session.setAttribute("userDiaryList", userDiaryList);
            
            // 각 다이어리의 정보를 콘솔에 출력
            for (UserDiary diary : userDiaryList) {
                System.out.println(diary.getDiaryId());
                System.out.println(diary.getDiaryTitle());
                System.out.println(diary.getDiaryContent());
                System.out.println(diary.getWriteDate());
            }
            
            // 페이지네이션을 위한 로직
            int pageSize = 4; // 페이지당 글 개수
            int totalCount = userDiaryList.size();
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCount);
            List<UserDiary> pageDiaryList = userDiaryList.subList(startIndex, endIndex);

            // 모델에 필요한 데이터 추가
            model.addAttribute("userDiaryList", pageDiaryList);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
            
            System.out.println(model.getAttribute("totalPages"));
            System.out.println(model.getAttribute("currentPage"));
            System.out.println(startIndex);//>=4
            System.out.println(totalCount);
            System.out.println(totalPages);
        }
        
        // 친구 목록을 가져옴
        FriendStatusDTO friendStatusDTO = new FriendStatusDTO();
        friendStatusDTO.setLoginUserId(userId);
        List<FriendStatusDTO> friendCountList = friendService.countFriends(friendStatusDTO);
        
        // 친구 수를 계산
        int friendCount = friendCountList.size();
        System.out.println("친구 수 : " + friendCount);

        // 모델에 친구 수와 사용자 정보를 추가
        model.addAttribute("friendCount", friendCount);
        model.addAttribute("userId", userId);
        
        ////팔로잉 팔로워
        // 로그인한 사용자의 ID로 팔로워 및 팔로잉 수를 가져옴
        int followerCount = friendService.countFollower(userId);
        int followingCount = friendService.countFollowing(userId);
        
        System.out.println("팔로워 수 : " + followerCount);
        System.out.println("팔로잉 수 : " + followingCount);

        // 모델에 팔로워 및 팔로잉 수 , 사용자 아이디 추가
        model.addAttribute("follower", followerCount);
        model.addAttribute("following", followingCount);
        model.addAttribute("userId", userId);
        

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

            return "redirect:/user/signin";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }

    // 유효성 검사 메서드
    //validateUserInput 메서드는 사용자의 입력을 검증하고, 입력이 올바르지 않으면 예외를 발생시킴.
    private void validateUserInput(User user) throws Exception {
        if (!Pattern.matches(verifyId, user.getUserId())) {	// user.getUserId() 값이 verifyId를 만족하지 않으면 예외처리
            throw new Exception("아이디 형식이 올바르지 않습니다. 4-20자의 영문자와 숫자만 사용 가능합니다.");
        }

        if (!Pattern.matches(verifyName, user.getUserName())) {	// user.getUserName() 값이 verifyName를 만족하지 않으면 예외처리
            throw new Exception("이름 형식이 올바르지 않습니다. 2-15자의 한글 또는 영문자만 사용 가능합니다.");
        }

        if (!Pattern.matches(verifyPassword, user.getUserPassword())) {	// user.getUserPassword() 값이 verifyPassword를 만족하지 않으면 예외처리
            throw new Exception("비밀번호는 4-20자이어야 하며, 최소 하나의 문자와 숫자를 포함해야 합니다.");
        }

        if (!Pattern.matches(verifyAddress, user.getUserAddress())) {	// user.getUserAddress() 값이 verifyAddress를 만족하지 않으면 예외처리
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
            System.out.println(session.getAttribute("user")+"정상");
            return "redirect:/main";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println(session.getAttribute("user")+"오류");
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
    
	// 주소변경
	@PostMapping("/user/modifyAddress")
	public String modifyAddress(User user, HttpSession session) {

		User sessionUser = (User) session.getAttribute("user");
		System.out.println(sessionUser);
		// 변경할 주소 가져오기

//  		System.out.println(user.getUserAddress());

		sessionUser.setUserAddress(user.getUserAddress());

		int result = userService.modifyAddress(sessionUser);

		if (result > 0) {
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}

		session.setAttribute("user", sessionUser);

		return "redirect:/main";
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
}
