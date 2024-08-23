package com.app.service.friend;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dto.friend.FriendDTO;
import com.app.dto.friend.FriendDiaryProfileDTO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;

public interface FriendService {
	
	// 사용자 input으로 user 검색
	public List<UserSearch> searchFriend(SearchFriend searchFriend);
	
	// 친구인지 아닌지 체크
	public boolean checkIfFriendOrNot(FriendStatusDTO friendStatusDTO);
	
	// 친구 신청
	public int joinRequestFriend(FriendStatusDTO friendStatusDTO);
	
	// 친구 신청 확인
	public List<User> confirmRequestFriend(String loginUserId);
	
	// 친구 신청 확인 후 친구를 받으면 친구 신청 table에서 삭제
	public int deleteRequestFriend(FriendStatusDTO friendStatusDTO);
	
	// 친구 신청을 받으면 사용자 -> 친구를 friendships table에 insert
	public int makeFriendsOneWay(FriendStatusDTO friendStatusDTO);
	
	// 친구 신청을 받으면 친구 -> 사용자를 friendships table에 insert
	public int makeFriendsTwoWay(FriendStatusDTO friendStatusDTO);
	
	// 친구 추천 리스트 조회
	public List<User> viewRecommendList(String loginUserId);

	// 팔로워 수 count
	public int countFollower(String userId);

	// 팔로잉 수 count
	public int countFollowing(String userId);
	
	// 친구의 일기 조회
	public List<FriendDiaryProfileDTO> getFriendsDiaryTimeline(String loginUserId);
	
	// 친구 목록 조회
	public List<FriendDTO> getFriendList(String loginUserId);
	
	// 친구 삭제 시 사용자 -> 친구를 friendships table에서 삭제
	public int unfollowFriendOneWay(FriendStatusDTO friendStatusDTO);
	
	// 친구 삭제 시 친구 -> 사용자를 friendships table에서 삭제
	public int unfollowFriendTwoWay(FriendStatusDTO friendStatusDTO);
}
