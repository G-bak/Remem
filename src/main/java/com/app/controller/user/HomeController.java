package com.app.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.user.User;
import com.app.service.user.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@GetMapping("/main")
	public String home(HttpSession session, Model model) {
		session.setAttribute("loginUserId", "user1");
		
		model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
		
		System.out.println(session.getAttribute("loginUserId"));
		
		return "main";
	}
	
	//친구추천
	@GetMapping("/usermain")
	public String userMain(HttpSession session, Model model) {
		// model.addAttribute("userId", "1");

		session.setAttribute("userId", "1");

		//System.out.println(session.getAttribute("userId").toString());

		List<User> myFriendList = userService.selectAllMyFriend(session.getAttribute("userId").toString());
		//System.out.println(myFriendList);

		model.addAttribute("myFriendList", myFriendList);

		List<User> temporaryFriendRecommendList = userService
				.findFriendRecommend(session.getAttribute("userId").toString());

		List<Integer> randomListIndex = new ArrayList<Integer>();
		Random random = new Random();

		while (randomListIndex.size() < 5) {
		    int randomIndex = random.nextInt(temporaryFriendRecommendList.size());
		    if (!randomListIndex.contains(randomIndex)) {
		        randomListIndex.add(randomIndex);
		    }
		}
		

		List<User> friendRecommendList = new ArrayList<User>();

		for (int i = 0; i < randomListIndex.size(); i++) {
			friendRecommendList.add(temporaryFriendRecommendList.get(randomListIndex.get(i)));
		}

		model.addAttribute("friendRecommendList", friendRecommendList);
		
		
		
		
		
		
		return "user/userMain";
	}
	
	//친구검색
	@PostMapping("/usermain")
	public String userMainAction(@RequestParam String serchUserId, Model model, HttpSession session) {

		User user = userService.findUserById(serchUserId);
		
		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("myId", session.getAttribute("userId").toString());
		paramMap.put("friendId", serchUserId);
		
		//친구인지 아닌지 체크
		boolean isFriend = userService.checkMyFriend(paramMap);
		
		model.addAttribute("isFriend", isFriend);
		
		model.addAttribute(user);

		return "user/userMain";
	}
	
	//친구추가
	@GetMapping("/join/friend")
	public String joinFriend(@RequestParam HashMap<String, String> paramMap) {
		paramMap.get("myId");
		paramMap.get("friendId");

		//System.out.println(paramMap.get("myId"));
		//System.out.println(paramMap.get("friendId"));

		int result = userService.joinFriendById(paramMap);

		if (result > 0) {
			return "user/userMain";
		} else {
			return "main";
		}

	}

	@PostMapping("/requestFriends")
	public String requestFriends(@RequestParam String confirmId, Model model) {
		List<User> requestFriendList = userService.selectFriendRequest(confirmId);

		model.addAttribute("requestFriendList", requestFriendList);

		return "user/requestFriends";
	}

	@PostMapping("/accept")
	public String acceptFriend(@RequestParam HashMap<String, String> paramMap) {

		System.out.println(paramMap.get("myId")); // 친구신청 받은사람
		System.out.println(paramMap.get("acceptUserId")); // 친구신청넣은사람

		int deleteResult = userService.deleteRequestFriend(paramMap);
		int insertResult1 = userService.makeFriendOneWay(paramMap);

		String temp = paramMap.get("myId");
		paramMap.put("myId", paramMap.get("acceptUserId"));
		paramMap.put("acceptUserId", temp);

		//System.out.println("값 교차" + paramMap.get("myId")); // 친구신청 받은사람
		//System.out.println("값 교차" + paramMap.get("acceptUserId")); // 친구신청넣은사람

		int insertResult2 = userService.makeFriendOneWay(paramMap);

		if (deleteResult + insertResult1 + insertResult2 >= 3) {
			return "redirect:/usermain";
		} else {
			return "main";
		}

	}

	@PostMapping("/myFriend")
	public String myFriendAction(@RequestParam String friendDetailId, Model model) {

		// 친구의 정보 조회
		//System.out.println(friendDetailId + "****");

		// model.addAllAttributes("", );
		return "user/friendDetail";
	}

	@GetMapping("/signup")
	public String signup() {
		return "user/signup";
	}

	@PostMapping("/signup")
	public String signupAction(@RequestParam HashMap<String, String> paramMap) {

		User user = new User();
		user.setUserId(paramMap.get("userId"));
		user.setUserName(paramMap.get("userName"));
		user.setAddress(paramMap.get("userAddress"));

		//System.out.println(user);

		return "";
	}
	
	
	
//	@ResponseBody
//	@PostMapping("/todoList/checked")
//	public String todoListCheckedAction(@RequestParam String todoListId) {
//		System.out.println("todoListId");
//		return todoListId;
//	}

}
