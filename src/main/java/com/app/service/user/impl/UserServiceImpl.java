package com.app.service.user.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.user.UserDAO;
import com.app.dto.user.User;
import com.app.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	UserDAO userDAO;
	
	

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
