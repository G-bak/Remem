package com.app.dao.accountBook;

import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;

public interface AccountBookDAO {
	public AccountBook viewAccountBook(AccountBookSearch abs);
}
