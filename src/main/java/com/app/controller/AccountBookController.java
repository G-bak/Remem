package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;
import com.app.service.accountBook.AccountBookService;


@Controller
public class AccountBookController {
    
	@Autowired
	AccountBookService accountBookService;
	
	@PostMapping("/view/AccountBook")
	public AccountBook viewAccountBook(@RequestParam String userId,@RequestParam String date) {
		AccountBookSearch abs = new AccountBookSearch(userId, date);
		AccountBook ab = accountBookService.viewAccountBook(abs);
		return ab;
	}

	
	
	
	/*@PostMapping("/insert/AccountBook")
	@PostMapping("/update/AccountBook")*/
	//날짜로 가계부 가져오기(select)
	//가계부 내용 저장하기(insert)
	
	//가계부 내용 업데이트하기(update)
	
	
	
	
}
