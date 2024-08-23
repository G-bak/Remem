package com.app.service.file;

import org.springframework.web.bind.annotation.RequestBody;

import com.app.dto.file.FileInfo;
import com.app.dto.friend.FriendStatusDTO;

public interface FileService {
	
	//프로필 사진 정보 저장
	public int saveFileInfo(FileInfo fileInfo);
	
	//userId로 프로필 사진 정보 조회
	public String findFileUrlByFileNameUserId(FileInfo fileInfo);
	
	// 프로필 사진이 있는지 체크
	public int selectFileInfo(FileInfo fileInfo);
	
	//프로필 사진이 있다면 update 철;
	public int updateFileInfo(FileInfo fileInfo);
	
	//파일 경로 조회
	public String findFilePathByUserId(String userId);
	
	
	
}
