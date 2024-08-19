package com.app.controller.accountbook;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;
import com.app.service.accountBook.AccountBookService;

@Controller
public class AccountBookController {

	@Autowired
	AccountBookService accountBookService;

	// 특정 날짜에 맞는 가계부 조회
	@ResponseBody
	@PostMapping("/view/AccountBook")
	public AccountBook viewAccountBook(@RequestBody Map<String, String> paramMap) {
		AccountBookSearch abs = new AccountBookSearch(paramMap.get("userId"), paramMap.get("accountDate"));
		AccountBook ab = accountBookService.viewAccountBook(abs);
		return ab;
	}

	// 가계부 저장
	@ResponseBody
	@PostMapping("/save/AccountBook")
	public AccountBook saveAccountBook(@RequestBody AccountBook accountBook) {
		System.out.println(accountBook);
		int savedAccountBook = accountBookService.saveAccountBook(accountBook);
		if (savedAccountBook > 0) {
			return accountBook;
		} else {
			return null;
		}
	}

	// 가계부 수정
	@ResponseBody
	@PostMapping("/modify/AccountBook")
	public AccountBook modifyAccountBook(@RequestBody AccountBook accountBook) {
		int modifyAccountBook = accountBookService.modifyAccountBook(accountBook);
		if (modifyAccountBook > 0) {
			return accountBook;
		} else {
			return null;
		}
	}

}
