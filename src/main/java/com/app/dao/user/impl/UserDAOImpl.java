package com.app.dao.user.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.user.UserDAO;
import com.app.dto.user.User;

@Repository
public class UserDAOImpl implements UserDAO{
	
	
	@Autowired				
	SqlSessionTemplate sqlSessionTemplate;
	
	
//	@Override
//	public User findUserById(String serchUserId) {
//		User user = sqlSessionTemplate.selectOne("user_mapper.findUserById", serchUserId);
//		return user;
//	}
//
//
//	@Override
//	public int joinFriendById(HashMap<String, String> paramMap) {
//		int result = sqlSessionTemplate.insert("user_mapper.joinFriendById", paramMap);
//		return result;
//	}
//
//
//	@Override
//	public List<User> selectFriendRequest(String confirmId) {
//		List<User> requestFriendList = sqlSessionTemplate.selectList("user_mapper.selectFriendRequest", confirmId);
//		//sqlSessionTemplate.selectList(serchUserId)
//		return requestFriendList;
//	}
//
//
//	@Override
//	public int deleteRequestFriend(HashMap<String, String> paramMap) {
//		int result = sqlSessionTemplate.delete("user_mapper.deleteRequestFriend", paramMap);
//		return result;
//	}
//
//
//	@Override
//	public int makeFriendOneWay(HashMap<String, String> paramMap) {
//		int result = sqlSessionTemplate.insert("user_mapper.makeFriend", paramMap);
//		return result;
//	}
//
//
//	@Override
//	public List<User> selectAllMyFriend(String myId) {
//		List<User> myFriendList = sqlSessionTemplate.selectList("user_mapper.selectAllMyFriend", myId);
//		return myFriendList;
//	}
//
//
//	@Override
//	public List<User> findFriendRecommend(String myId) {
//		List<User> friendRecommendList = sqlSessionTemplate.selectList("user_mapper.findFriendRecommend", myId);
//		return friendRecommendList;
//	}
//
//
//	@Override
//	public boolean checkMyFriend(HashMap<String, String> paramMap) {
//		String result = sqlSessionTemplate.selectOne("user_mapper.checkMyFriend", paramMap); 
//		//true면 친구 false면 친구아님
//		
//		boolean isFriend = Boolean.parseBoolean(result);
//		
//		return isFriend;
//	}


	@Override
	public int saveUser(User user) {
		int result = sqlSessionTemplate.insert("user_mapper.saveUser", user);
		return result;
	}


	@Override
	public User loginUser(User user) {
		User userList = sqlSessionTemplate.selectOne("user_mapper.loginUser", user);
		return userList;
	}


	@Override
	public int removeUser(String userId) {
		int result = sqlSessionTemplate.delete("user_mapper.removeUser", userId);
		return result;
	}

	@Override
	public User findUserById(String userId) {
		User result = sqlSessionTemplate.selectOne("user_mapper.findUserById", userId);
		return result;
	}

	@Override
	public int modifyAddress(User user) {
		int result = sqlSessionTemplate.update("user_mapper.modifyAddress", user);
		return result;
	}


	@Override
	public int modifyPassword(User user) {
		int result = sqlSessionTemplate.update("user_mapper.modifyPassword", user);
		return result;
	}
	
	

	
}
