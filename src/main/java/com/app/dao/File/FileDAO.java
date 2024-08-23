package com.app.dao.File;

import com.app.dto.file.FileInfo;
import com.app.dto.friend.FriendStatusDTO;

public interface FileDAO {
	
	//프로필 사진 저장
	public int saveFileInfo(FileInfo fileInfo);
	
	//userId로 프로필 url 조회
	public String findFileUrlByFileNameUserId(FileInfo fileInfo);
	
	//저장된 프로필 사진이 있는지 조회
	public int selectFileInfo(FileInfo fileInfo);
	
	//저장된 프로필 사진이 있다면 프로필 사진 update
	public int updateFileInfo(FileInfo fileInfo);
	
	// userId로 파일 경로 조회
	public String findFilePathByUserId(String userId);
	
	
	
}


