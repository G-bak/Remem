package com.app.service.file;

import org.springframework.web.bind.annotation.RequestBody;

import com.app.dto.file.FileInfo;
import com.app.dto.friend.FriendStatusDTO;

public interface FileService {
	
	public int saveFileInfo(FileInfo fileInfo);
	
	public String findFileUrlByFileNameUserId(FileInfo fileInfo);
	
	public int selectFileInfo(FileInfo fileInfo);
	
	public int updateFileInfo(FileInfo fileInfo);
	
	public String findFilePathByUserId(String userId);
	
	
	
}
