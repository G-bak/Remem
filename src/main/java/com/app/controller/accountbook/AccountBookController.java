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
	
	@ResponseBody
	@PostMapping("/view/AccountBook")
	public AccountBook viewAccountBook(@RequestBody  Map<String, String> paramMap) {
		AccountBookSearch abs = new AccountBookSearch(paramMap.get("userId"), paramMap.get("accountDate") );
		AccountBook ab = accountBookService.viewAccountBook(abs);
		return ab;
	}
	
	@ResponseBody
	@PostMapping("/save/AccountBook")
	//@PostMapping("/update/AccountBook")
	public AccountBook saveAccountBook(@RequestBody AccountBook accountBook) {
		System.out.println(accountBook);
	    int savedAccountBook = accountBookService.saveAccountBook(accountBook);
	    if(savedAccountBook > 0) {
	    	return accountBook;
	    }else {
	    	return null;
	    }
	}
	
	@ResponseBody
	@PostMapping("/modify/AccountBook")
	public AccountBook modifyAccountBook(@RequestBody AccountBook accountBook) {
		int modifyAccountBook = accountBookService.modifyAccountBook(accountBook);
		if(modifyAccountBook > 0) {
		    	return accountBook;
		    }else {
		    	return null;
		    }
	}
	

	
	
	
	/*@PostMapping("/insert/AccountBook")
	@PostMapping("/update/AccountBook")*/
	//날짜로 가계부 가져오기(select)
	//가계부 내용 저장하기(insert)
	
	//가계부 내용 업데이트하기(update)
	
	
	
	
}
