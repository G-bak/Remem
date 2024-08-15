package com.app.dto.friend;

import lombok.Data;

@Data
public class FriendDiaryProfileDTO {
	String userId;	//친구 아이디
	String diaryTitle;	//일기 제목
	String writeDate;	//일기 작성 날짜
	String diaryContent;	//일기 내용
	String urlFilePath;	//프로필 사진 주소
}
