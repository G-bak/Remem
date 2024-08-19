package com.app.service.friend;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;

public interface FriendService {
	public List<UserSearch> searchFriend(SearchFriend searchFriend);
	
	public boolean checkIfFriendOrNot(FriendStatusDTO friendStatusDTO);
	
	public int joinRequestFriend(FriendStatusDTO friendStatusDTO);
	
	public List<User> confirmRequestFriend(String loginUserId);
	
	public int deleteRequestFriend(FriendStatusDTO friendStatusDTO);
	
	public int makeFriendsOneWay(FriendStatusDTO friendStatusDTO);
	
	public int makeFriendsTwoWay(FriendStatusDTO friendStatusDTO);
	
	public List<User> viewRecommendList(String loginUserId);

	public List<FriendStatusDTO> countFriends(FriendStatusDTO friendStatusDTO);

	public int countFollower(String userId);

	public int countFollowing(String userId);
	
	
	
}
