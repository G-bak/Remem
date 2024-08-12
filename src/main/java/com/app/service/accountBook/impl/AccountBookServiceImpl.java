package com.app.service.accountBook.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.accountBook.AccountBookDAO;
import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;
import com.app.service.accountBook.AccountBookService;

@Service
public class AccountBookServiceImpl implements AccountBookService{
	
	@Autowired
	AccountBookDAO accountBookDAO;

	@Override
	public AccountBook viewAccountBook(AccountBookSearch abs) {
		// TODO Auto-generated method stub
		AccountBook acb = accountBookDAO.viewAccountBook(abs);
		return acb;
	}

	@Override
	public int saveAccountBook(AccountBook accountBook) {
		// TODO Auto-generated method stub
		int saveAccountBook = accountBookDAO.saveAccountBook(accountBook);
		return saveAccountBook;
	}

	@Override
	public int modifyAccountBook(AccountBook accountBook) {
		// TODO Auto-generated method stub
		int modifyAccountBook = accountBookDAO.modifyAccountBook(accountBook);
		return modifyAccountBook;
	}
}
