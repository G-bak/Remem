package com.app.dao.friend.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.friend.FriendDAO;
import com.app.dto.friend.FriendDTO;
import com.app.dto.friend.FriendDiaryProfileDTO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;

@Repository
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<UserSearch> searchFriend(SearchFriend searchFriend) {
		List<UserSearch> searchedFriendList = sqlSessionTemplate.selectList("friend_mapper.searchFriend", searchFriend);
		return searchedFriendList;
	}

	@Override
	public boolean checkIfFriendOrNot(FriendStatusDTO friendStatusDTO) {
		int result = sqlSessionTemplate.selectOne("friend_mapper.checkIfFriendOrNot", friendStatusDTO);

		if (result > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int joinRequestFriend(FriendStatusDTO friendStatusDTO) {
		int result = sqlSessionTemplate.insert("friend_mapper.joinRequestFriend", friendStatusDTO);
		return result;
	}

	@Override
	public List<User> confirmRequestFriend(String loginUserId) {
		List<User> requestFriendList = sqlSessionTemplate.selectList("friend_mapper.confirmRequestFriend", loginUserId);
		return requestFriendList;
	}

	@Override
	public int deleteRequestFriend(FriendStatusDTO friendStatusDTO) {
		int deleteResult = sqlSessionTemplate.delete("friend_mapper.deleteRequestFriend", friendStatusDTO);
		return deleteResult;
	}

	@Override
	public int makeFriendsOneWay(FriendStatusDTO friendStatusDTO) {
		int insertResult = sqlSessionTemplate.insert("friend_mapper.makeFriendsOneWay", friendStatusDTO);
		return insertResult;
	}

	@Override
	public int makeFriendsTwoWay(FriendStatusDTO friendStatusDTO) {
		int insertResult = sqlSessionTemplate.insert("friend_mapper.makeFriendsTwoWay", friendStatusDTO);
		return insertResult;
	}

	@Override
	public List<User> viewRecommendList(String loginUserId) {
		List<User> recommendList = sqlSessionTemplate.selectList("friend_mapper.viewRecommendList", loginUserId);

		return recommendList;
	}

	@Override
	public List<FriendDiaryProfileDTO> getFriendsDiaryTimeline(String loginUserId) {
		List<FriendDiaryProfileDTO> friendDiaryProfileList = sqlSessionTemplate.selectList("friend_mapper.getFriendsDiaryTimeline", loginUserId);
		return friendDiaryProfileList;
	}

	@Override
	public List<FriendDTO> getFriendList(String loginUserId) {
		List<FriendDTO> friendList = sqlSessionTemplate.selectList("friend_mapper.getFriendList", loginUserId);
		return friendList;
	}

	@Override
	public int unfollowFriendOneWay(FriendStatusDTO friendStatusDTO) {
		int unfollowResultOneWay = sqlSessionTemplate.delete("friend_mapper.unfollowFriendOneWay", friendStatusDTO);
		return unfollowResultOneWay;
	}

	@Override
	public int unfollowFriendTwoWay(FriendStatusDTO friendStatusDTO) {
		int unfollowResultTwoWay = sqlSessionTemplate.delete("friend_mapper.unfollowFriendTwoWay", friendStatusDTO);
		return unfollowResultTwoWay;
	}

}
