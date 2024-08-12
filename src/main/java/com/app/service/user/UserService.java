package com.app.service.user;

import java.util.HashMap;
import java.util.List;

import com.app.dto.user.User;

public interface UserService {

	
	public int saveUser(User user);

	public User loginUser(User user);

	public int removeUser(String userId);
	
	public User findUserById(String userId);

	public int modifyAddress(User user);

	public int modifyPassword(User user);


	
}
