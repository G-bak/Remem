package com.app.dao.friend.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.friend.FriendDAO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;

@Repository
public class FriendDAOImpl implements FriendDAO{
	
	
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
		
		if(result > 0){
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

}
