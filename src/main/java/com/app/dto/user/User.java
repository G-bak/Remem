package com.app.dto.user;

import lombok.Data;

@Data
public class User {
	String userId;		//유저 아이디
	String userName;	//유저 이름
	String userAddress;		//유저 주소
//	String createdAt;	//계정 생성일
}
