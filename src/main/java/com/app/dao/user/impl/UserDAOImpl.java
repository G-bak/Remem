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
