package com.app.service.friend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.friend.FriendDAO;
import com.app.dto.friend.FriendDTO;
import com.app.dto.friend.FriendDiaryProfileDTO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;
import com.app.service.friend.FriendService;

@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	FriendDAO friendDAO;

	@Override
	public List<UserSearch> searchFriend(SearchFriend searchFriend) {
		List<UserSearch> searchedFriendList = friendDAO.searchFriend(searchFriend);
		return searchedFriendList;
	}

	@Override
	public boolean checkIfFriendOrNot(FriendStatusDTO friendStatusDTO) {
		boolean isFriend = friendDAO.checkIfFriendOrNot(friendStatusDTO);
		return isFriend;
	}

	@Override
	public int joinRequestFriend(FriendStatusDTO friendStatusDTO) {
		int result = friendDAO.joinRequestFriend(friendStatusDTO);
		return result;
	}

	@Override
	public List<User> confirmRequestFriend(String loginUserId) {
		List<User> requestFriendList = friendDAO.confirmRequestFriend(loginUserId);
		return requestFriendList;
	}

	@Override
	public int deleteRequestFriend(FriendStatusDTO friendStatusDTO) {
		int deleteResult = friendDAO.deleteRequestFriend(friendStatusDTO);
		return deleteResult;
	}

	@Override
	public int makeFriendsOneWay(FriendStatusDTO friendStatusDTO) {
		int insertResult = friendDAO.makeFriendsOneWay(friendStatusDTO);
		return insertResult;
	}

	@Override
	public int makeFriendsTwoWay(FriendStatusDTO friendStatusDTO) {
		int insertResult = friendDAO.makeFriendsTwoWay(friendStatusDTO);
		return insertResult;
	}

	@Override
	public List<User> viewRecommendList(String loginUserId) {
		List<User> recommendList = friendDAO.viewRecommendList(loginUserId);

		return recommendList;
	}

	@Override
	public List<FriendDiaryProfileDTO> getFriendsDiaryTimeline(String loginUserId) {
		List<FriendDiaryProfileDTO> friendDiaryProfileList = friendDAO.getFriendsDiaryTimeline(loginUserId);
		return friendDiaryProfileList;
	}

	@Override
	public List<FriendDTO> getFriendList(String loginUserId) {
		List<FriendDTO> friendList = friendDAO.getFriendList(loginUserId);
		return friendList;
	}

}
