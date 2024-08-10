package com.app.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class User {
	
	@NotBlank(message = "아이디는 필수 항목입니다.")
	@Size(min = 4, max=20, message = "아이디는 4-20자리이어야 합니다.")
	String userId;		//유저 아이디
	
	@NotBlank(message = "이름은 필수 항목입니다.")
	@Size(min = 2, max=15, message = "이름은 2-15자리이어야 합니다.")
	String userName;	//유저 이름
	
	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	@Size(min = 4, message = "비밀번호는 문자와 숫자를 조합하여 최소 4자 이상이어야 합니다.")
	String userPassword; //유저 비밀번호
	
	@NotBlank(message = "주소는 필수 항목입니다.")
	@Size(min = 8, max=20, message = "주소는 8-20자리이어야 합니다.")
	String userAddress;		//유저 주소
//	String createdAt;	//계정 생성일
}
