package com.app.dao.accountBook;

import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;

public interface AccountBookDAO {
	//가계부 조회
	public AccountBook viewAccountBook(AccountBookSearch abs);
	
	//가계부 저장
	public int saveAccountBook(AccountBook accountBook);
	
	//가계부 수정
	public int modifyAccountBook(AccountBook accountBook);
}
