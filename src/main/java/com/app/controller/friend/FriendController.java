package com.app.controller.friend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;
import com.app.service.friend.FriendService;

@Controller
public class FriendController {

	@Autowired
	FriendService friendService;

	@PostMapping("/searchFriend")
	@ResponseBody
	public List<UserSearch> searchFriend(@RequestBody SearchFriend searchFriend) {
		// 친구검색 메소드 3명으로 뽑아냄 List로 (사용자 input으로만 뽑아냄)
		// System.out.println(searchFriend.getUserInput());

		List<UserSearch> searchedFriendList = friendService.searchFriend(searchFriend);
		// System.out.println(searchedFriendList);

		// List에 있는 친구들이 나랑 현재 친구인지 아닌지 체크 (내 아이디 & 친구아이디)
		FriendStatusDTO friendStatusDTO = null;

		for (int i = 0; i < searchedFriendList.size(); i++) {
			friendStatusDTO = new FriendStatusDTO();
			friendStatusDTO.setLoginUserId(searchFriend.getLoginUserId()); // 내 아이디
			friendStatusDTO.setFriendId(searchedFriendList.get(i).getUserId()); // 위에서 검색된 친구아이디

			searchedFriendList.get(i).setFriend(friendService.checkIfFriendOrNot(friendStatusDTO));
		}
		// true이면 친구, false이면 친구 아님
//		for(int i = 0; i < searchedFriendList.size(); i++) {
//			System.out.println(searchedFriendList.get(i).getUserId());
//			System.out.println(searchedFriendList.get(i).isFriend());
//		}
		return searchedFriendList;
	}

	@PostMapping("/joinRequestFriend")
	@ResponseBody
	public int joinRequestFriend(@RequestBody FriendStatusDTO friendStatusDTO) {
		int result = friendService.joinRequestFriend(friendStatusDTO);

		return result;
	}

	@PostMapping("/confirmRequestFriend")
	@ResponseBody
	public List<User> confirmRequestFriend(@RequestParam String loginUserId) {
		List<User> requestFriendList = friendService.confirmRequestFriend(loginUserId);
		System.out.println(requestFriendList);
		return requestFriendList;
	}

	@PostMapping("/receiveFriendRequest")
	@ResponseBody
	public List<User> receiveFriendRequest(@RequestBody FriendStatusDTO friendStatusDTO){
		//친구신청함에서 삭제
		int deleteResult = friendService.deleteRequestFriend(friendStatusDTO);
		
		//친구테이블에 둘다 번갈아서 넣기
		int insertResultOnWay = friendService.makeFriendsOneWay(friendStatusDTO);
		
		int insertResultTwoWay = friendService.makeFriendsTwoWay(friendStatusDTO);
		
		List<User> requestFriendList = null;
		if(deleteResult + insertResultOnWay + insertResultTwoWay >= 3) {
			requestFriendList = friendService.confirmRequestFriend(friendStatusDTO.getLoginUserId());
		}
		
		return requestFriendList;
	}
	
	
	
	
	
	
	@PostMapping("/viewRecommendList")
	@ResponseBody
	public List<User> viewRecommendList(@RequestParam String loginUserId){
		List<User> recommendList = friendService.viewRecommendList(loginUserId);
		
		return recommendList;
	}
	
	
	
	
	

}
