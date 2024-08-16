package com.app.dao.friend;

import java.util.List;

import com.app.dto.friend.FriendDTO;
import com.app.dto.friend.FriendDiaryProfileDTO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;

public interface FriendDAO {
	public List<UserSearch> searchFriend(SearchFriend searchFriend);
	
	public boolean checkIfFriendOrNot(FriendStatusDTO friendStatusDTO);
	
	public int joinRequestFriend(FriendStatusDTO friendStatusDTO);
	
	public List<User> confirmRequestFriend(String loginUserId);
	
	public int deleteRequestFriend(FriendStatusDTO friendStatusDTO);
	
	public int makeFriendsOneWay(FriendStatusDTO friendStatusDTO);
	
	public int makeFriendsTwoWay(FriendStatusDTO friendStatusDTO);
	
	public List<User> viewRecommendList(String loginUserId);
	
	public List<FriendDiaryProfileDTO> getFriendsDiaryTimeline(String loginUserId);
	
	public List<FriendDTO> getFriendList(String loginUserId);
	
	public int unfollowFriendOneWay(FriendStatusDTO friendStatusDTO);
	
	public int unfollowFriendTwoWay(FriendStatusDTO friendStatusDTO);
	
	
}
