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

	@Override
	public int saveAccountBook(AccountBook accountBook) {
		// TODO Auto-generated method stub
		int saveAccountBook = sqlSessionTemplate.insert("accountBook_mapper.saveAccountBook", accountBook);
		return saveAccountBook;
	}

	@Override
	public int modifyAccountBook(AccountBook accountBook) {
		// TODO Auto-generated method stub
		int modifyAccountBook = sqlSessionTemplate.update("accountBook_mapper.modifyAccountBook", accountBook);
		return modifyAccountBook;
	}
}
