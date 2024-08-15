package com.app.dto.api;

import lombok.Data;

@Data
public class ApiResponseHeader {
	
	String resultCode;		//00 03 99 
	String resultMessage;
}
