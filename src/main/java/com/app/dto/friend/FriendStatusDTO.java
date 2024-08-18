package com.app.dto.friend;

import lombok.Data;

@Data
public class FriendStatusDTO {
	String loginUserId; //내 아이디
	String friendId; // 친구아이디
	int friendCount; // 친구 수
}
