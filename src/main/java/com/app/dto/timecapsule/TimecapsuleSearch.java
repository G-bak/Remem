package com.app.dto.timecapsule;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimecapsuleSearch {
	
	String tcUserId;	//사용자 아이디
    String tcDate;      //날짜
}
