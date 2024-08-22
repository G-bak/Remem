package com.app.dao.user;

import java.util.HashMap;
import java.util.List;

import com.app.dto.user.User;

public interface UserDAO {


	public int saveUser(User user);

	public User loginUser(User user);

	public int removeUser(String userId);

	public User findUserById(String userId);

	public int modifyAddress(User user);

	public int modifyPassword(User user);
	
	public int checkDuplicatedId(String signupId);

}
