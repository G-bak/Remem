package com.app.dao;

import java.util.HashMap;
import java.util.List;

import com.app.dto.user.User;

public interface UserDAO {
	public User findUserById(String serchUserId);
	
	public int joinFriendById(HashMap<String, String> paramMap);
	
	public List<User> selectFriendRequest(String confirmId);
	
	public int deleteRequestFriend(HashMap<String, String> paramMap);
	
	public int makeFriendOneWay(HashMap<String, String> paramMap);
	
	public List<User> selectAllMyFriend(String myId);
	
	public List<User> findFriendRecommend(String myId);
	
	public boolean checkMyFriend(HashMap<String, String> paramMap);
}
