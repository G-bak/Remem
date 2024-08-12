package com.app.service;

import java.util.HashMap;
import java.util.List;

import com.app.dto.user.User;

public interface UserService {
//	public User findUserById(String serchUserId);
//	
//	public int joinFriendById(HashMap<String, String> paramMap);
//	
//	public List<User> selectFriendRequest(String confirmId);
//	
//	public int deleteRequestFriend(HashMap<String, String> paramMap);
//	
//	public int makeFriendOneWay(HashMap<String, String> paramMap);
//	
//	public List<User> selectAllMyFriend(String myId);
//	
//	public List<User> findFriendRecommend(String myId);
//	
//	public boolean checkMyFriend(HashMap<String, String> paramMap);
	
	public int saveUser(User user);

	public User loginUser(User user);

	public int removeUser(String userId);
	
	public User findUserById(String userId);

	public int modifyAddress(User user);

	public int modifyPassword(User user);


	
}
