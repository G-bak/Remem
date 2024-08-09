package com.app.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.UserDAO;
import com.app.dto.user.User;
import com.app.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	UserDAO userDAO;
	
	
//	@Override
//	public User findUserById(String serchUserId) {
//		User user = userDAO.findUserById(serchUserId);
//		
//		return user;
//	}
//
//
//	@Override
//	public int joinFriendById(HashMap<String, String> paramMap) {
//		int result = userDAO.joinFriendById(paramMap);
//		return result;
//	}
//
//
//	@Override
//	public List<User> selectFriendRequest(String confirmId) {
//		List<User> requestFriendList = userDAO.selectFriendRequest(confirmId);
//		return requestFriendList;
//	}
//
//
//	@Override
//	public int deleteRequestFriend(HashMap<String, String> paramMap) {
//		int result = userDAO.deleteRequestFriend(paramMap);
//		return result;
//	}
//
//
//	@Override
//	public int makeFriendOneWay(HashMap<String, String> paramMap) {
//		int result = userDAO.makeFriendOneWay(paramMap);
//		return result;
//	}
//
//
//	@Override
//	public List<User> selectAllMyFriend(String myId) {
//		List<User> myFriendList = userDAO.selectAllMyFriend(myId);
//		return myFriendList;
//	}
//
//
//	@Override
//	public List<User> findFriendRecommend(String myId) {
//		List<User> friendRecommendList = userDAO.findFriendRecommend(myId);
//		return friendRecommendList;
//	}
//
//
//	@Override
//	public boolean checkMyFriend(HashMap<String, String> paramMap) {
//		boolean isFriend = userDAO.checkMyFriend(paramMap);
//		return isFriend;
//	}


	@Override
	public int saveUser(User user) {
		int result = userDAO.saveUser(user);
		return result;
	}


	@Override
	public User loginUser(User user) {
		User userList = userDAO.loginUser(user);
		return userList;
	}


	@Override
	public int removeUser(String userId) {
		int result = userDAO.removeUser(userId);
		return result;
	}


	@Override
	public User findUserById(String userId) {
		User result = userDAO.findUserById(userId);
		return result;
	}
	
	@Override
	public int modifyAddress(User user) {
		int result = userDAO.modifyAddress(user);
		return result;
	}


	@Override
	public int modifyPassword(User user) {
		int result = userDAO.modifyPassword(user);
		return result;
	}

	

}
