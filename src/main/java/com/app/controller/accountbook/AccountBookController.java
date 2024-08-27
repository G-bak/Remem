package com.app.controller.accountbook;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;
import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.service.accountBook.AccountBookService;

@Controller
public class AccountBookController {

	@Autowired
	AccountBookService accountBookService;

	// 특정 날짜에 맞는 가계부 조회
	@ResponseBody
	@PostMapping("/view/AccountBook")
	public ApiResponse<AccountBook> viewAccountBook(@RequestBody Map<String, String> paramMap) {
		ApiResponse<AccountBook> apiResponse = new ApiResponse<>();
		ApiResponseHeader apiHeader = new ApiResponseHeader();

		try {
			if (paramMap == null || !paramMap.containsKey("userId") || !paramMap.containsKey("accountDate")) {
				apiHeader.setResultCode("99");
				apiHeader.setResultMessage("Missing required parameters: userId or accountDate");
				apiResponse.setHeader(apiHeader);
				return apiResponse;
			}

			AccountBookSearch abs = new AccountBookSearch(paramMap.get("userId"), paramMap.get("accountDate"));
			AccountBook accountBook = accountBookService.viewAccountBook(abs);

			if (accountBook != null) {
				apiHeader.setResultCode("00");
				apiHeader.setResultMessage("AccountBook retrieved successfully");
				apiResponse.setBody(accountBook);
			} else {
				apiHeader.setResultCode("99");
				apiHeader.setResultMessage("AccountBook not found");
			}

		} catch (Exception e) {
			apiHeader.setResultCode("99");
			apiHeader.setResultMessage("Error occurred while retrieving AccountBook: " + e.getMessage());
		}

		apiResponse.setHeader(apiHeader);
		return apiResponse;
	}

	// 가계부 저장
	@ResponseBody
	@PostMapping("/save/AccountBook")
	public ApiResponse<AccountBook> saveAccountBook(@RequestBody AccountBook accountBook) {
		ApiResponse<AccountBook> apiResponse = new ApiResponse<>();
		ApiResponseHeader apiHeader = new ApiResponseHeader();

		try {
			int result = accountBookService.saveAccountBook(accountBook);
			if (result > 0) {
				apiHeader.setResultCode("00");
				apiHeader.setResultMessage("AccountBook saved successfully");
				apiResponse.setBody(accountBook);
			} else {
				apiHeader.setResultCode("99");
				apiHeader.setResultMessage("Failed to save AccountBook");
				apiResponse.setBody(null);
			}
		} catch (Exception e) {
			apiHeader.setResultCode("99");
			apiHeader.setResultMessage("Error occurred while saving AccountBook: " + e.getMessage());
			apiResponse.setBody(null);
		}

		apiResponse.setHeader(apiHeader);
		return apiResponse;
	}

	// 가계부 수정
	@ResponseBody
	@PostMapping("/modify/AccountBook")
	public ApiResponse<AccountBook> modifyAccountBook(@RequestBody AccountBook accountBook) {
		ApiResponse<AccountBook> apiResponse = new ApiResponse<>();
		ApiResponseHeader apiHeader = new ApiResponseHeader();

		try {
			int result = accountBookService.modifyAccountBook(accountBook);
			if (result > 0) {
				apiHeader.setResultCode("00");
				apiHeader.setResultMessage("AccountBook modified successfully");
				apiResponse.setBody(accountBook);
			} else {
				apiHeader.setResultCode("99");
				apiHeader.setResultMessage("Failed to modify AccountBook");
				apiResponse.setBody(null);
			}
		} catch (Exception e) {
			apiHeader.setResultCode("99");
			apiHeader.setResultMessage("Error occurred while modifying AccountBook: " + e.getMessage());
			apiResponse.setBody(null);
		}

		apiResponse.setHeader(apiHeader);
		return apiResponse;
	}

}
