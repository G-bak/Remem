package com.app.controller.friend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.friend.FriendDTO;
import com.app.dto.friend.FriendDiaryProfileDTO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;
import com.app.service.file.FileService;
import com.app.service.friend.FriendService;

@Controller
public class FriendController {

	@Autowired
	FriendService friendService;

	@Autowired
	FileService fileService;

	// 친구 검색
	@PostMapping("/searchFriend")
	@ResponseBody
	public List<UserSearch> searchFriend(@RequestBody SearchFriend searchFriend) {
		// 친구검색 메소드 3명으로 뽑아냄 List로 (사용자 input으로만 뽑아냄)
		List<UserSearch> searchedFriendList = friendService.searchFriend(searchFriend);

		// List에 있는 친구들이 나랑 현재 친구인지 아닌지 체크 (내 아이디 & 친구아이디)
		FriendStatusDTO friendStatusDTO = null;

		for (int i = 0; i < searchedFriendList.size(); i++) {
			friendStatusDTO = new FriendStatusDTO();
			friendStatusDTO.setLoginUserId(searchFriend.getLoginUserId()); // 내 아이디
			friendStatusDTO.setFriendId(searchedFriendList.get(i).getUserId()); // 위에서 검색된 친구아이디

			searchedFriendList.get(i).setFriend(friendService.checkIfFriendOrNot(friendStatusDTO));
		}

		// 사용자 프로필 주소 설정
		for (int i = 0; i < searchedFriendList.size(); i++) {
			searchedFriendList.get(i)
					.setUrlFilePath(fileService.findFilePathByUserId(searchedFriendList.get(i).getUserId()));
		}

		return searchedFriendList;
	}

	// 친구 신청
	@PostMapping("/joinRequestFriend")
	@ResponseBody
	public int joinRequestFriend(@RequestBody FriendStatusDTO friendStatusDTO) {
		
		System.out.println(friendStatusDTO);
		int result = friendService.joinRequestFriend(friendStatusDTO);

		return result;
	}

	// 친구 신청함 확인
	@PostMapping("/confirmRequestFriend")
	@ResponseBody
	public List<User> confirmRequestFriend(@RequestParam String loginUserId) {
		List<User> requestFriendList = friendService.confirmRequestFriend(loginUserId);
		System.out.println(requestFriendList);

		for (int i = 0; i < requestFriendList.size(); i++) {
			requestFriendList.get(i)
					.setUrlFilePath(fileService.findFilePathByUserId(requestFriendList.get(i).getUserId()));
		}

		return requestFriendList;
	}

	// 친구 신청함에서 친구 신청 받기
	@PostMapping("/receiveFriendRequest")
	@ResponseBody
	public List<User> receiveFriendRequest(@RequestBody FriendStatusDTO friendStatusDTO) {
		// 친구신청함에서 삭제
		int deleteResult = friendService.deleteRequestFriend(friendStatusDTO);

		// 친구테이블에 둘다 번갈아서 넣기
		int insertResultOnWay = friendService.makeFriendsOneWay(friendStatusDTO);

		int insertResultTwoWay = friendService.makeFriendsTwoWay(friendStatusDTO);

		List<User> requestFriendList = null;
		if (deleteResult + insertResultOnWay + insertResultTwoWay >= 3) {
			requestFriendList = friendService.confirmRequestFriend(friendStatusDTO.getLoginUserId());
		}
		if (requestFriendList != null) {
			for (int i = 0; i < requestFriendList.size(); i++) {
				requestFriendList.get(i)
						.setUrlFilePath(fileService.findFilePathByUserId(requestFriendList.get(i).getUserId()));
			}
		}

		return requestFriendList;
	}

	// 친구 추천리스트
	@PostMapping("/viewRecommendList")
	@ResponseBody
	public List<User> viewRecommendList(@RequestParam String loginUserId) {
		List<User> recommendList = friendService.viewRecommendList(loginUserId);

		for (int i = 0; i < recommendList.size(); i++) {
			recommendList.get(i).setUrlFilePath(fileService.findFilePathByUserId(recommendList.get(i).getUserId()));
		}

		return recommendList;
	}

	// 친구 일기 조회(친구들 정보 + 일기 정보 + 프로필 사진 주소)
	@PostMapping("getFriendsDiaryTimeline")
	@ResponseBody
	public List<FriendDiaryProfileDTO> getFriendsDiaryTimeline(@RequestParam String loginUserId) {
		List<FriendDiaryProfileDTO> friendDiaryProfileList = friendService.getFriendsDiaryTimeline(loginUserId);

		return friendDiaryProfileList;
	}

	// 친구 목록 조회
	@PostMapping("getFriendList")
	@ResponseBody
	public List<FriendDTO> getFriendList(@RequestParam String loginUserId) {
		List<FriendDTO> friendList = friendService.getFriendList(loginUserId);

		return friendList;
	}

	// 친구 언팔로우(친구 삭제)
	@PostMapping("unfollowFriend")
	@ResponseBody
	public String unfollowFriend(@RequestBody FriendStatusDTO friendStatusDTO) {

		System.out.println(friendStatusDTO);

		int unfollowResultOneWay = friendService.unfollowFriendOneWay(friendStatusDTO);

		int unfollowResultTwoWay = friendService.unfollowFriendTwoWay(friendStatusDTO);

		if (unfollowResultOneWay + unfollowResultTwoWay >= 2) {
			return "언팔로우가 완료되었습니다.";
		} else {
			return "언팔로우 실패";
		}

	}

}
