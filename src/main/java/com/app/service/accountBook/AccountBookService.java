package com.app.service.accountBook;

import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;

public interface AccountBookService {
	
	public AccountBook viewAccountBook(AccountBookSearch abs);
	
	public int saveAccountBook(AccountBook accountBook);
	
	public int modifyAccountBook(AccountBook accountBook);
}
