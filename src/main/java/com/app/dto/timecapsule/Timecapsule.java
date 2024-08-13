package com.app.dto.timecapsule;

import lombok.Data;

@Data
public class Timecapsule {
	int tcId; 			//PK
    String tcUserId;	//사용자 아이디
    String tcDate;      //날짜
    String tcContent; 	//내용
}