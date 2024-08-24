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
		
		try {
			int result = userDAO.saveUser(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}


	@Override
	public User loginUser(User user) {
		try {
			User loginUser = userDAO.loginUser(user);
			return loginUser;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		
	}


	@Override
	public int removeUser(String userId) {
		try {
			int result = userDAO.removeUser(userId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}


	@Override
	public User findUserById(String userId) {
		
		User result = userDAO.findUserById(userId);
		return result;
		
	}
	
	@Override
	public int modifyAddress(User user) {
		try {
			int result = userDAO.modifyAddress(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}


	@Override
	public int modifyPassword(User user) {
		try {
			int result = userDAO.modifyPassword(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}


	@Override
	public int checkDuplicatedId(String signupId) {
		int result = userDAO.checkDuplicatedId(signupId);
		return result;
	}


	

}
