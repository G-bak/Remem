package com.app.dao.accountBook.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.app.dao.accountBook.AccountBookDAO;
import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;

@Repository
public class AccountBookDAOImpl implements AccountBookDAO {

	@Autowired				
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public AccountBook viewAccountBook(AccountBookSearch abs) {
		// TODO Auto-generated method stub
		AccountBook acb = sqlSessionTemplate.selectOne("accountBook_mapper.viewAccountBook",abs);
		return acb;
	}
}
