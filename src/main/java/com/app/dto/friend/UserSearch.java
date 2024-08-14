package com.app.dto.friend;

import lombok.Data;

@Data
public class UserSearch {
	String userId; //검색된 친구 아이디
	boolean isFriend; //친구인지 아닌지 체크
	
	String urlFilePath; //프로필주소
}
