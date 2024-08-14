package com.app.service.file;

import com.app.dto.file.FileInfo;

public interface FileService {
	
	public int saveFileInfo(FileInfo fileInfo);
	
	public String findFileUrlByFileNameUserId(FileInfo fileInfo);
	
	public int selectFileInfo(FileInfo fileInfo);
	
	public int updateFileInfo(FileInfo fileInfo);
	
	public String findFilePathByUserId(String userId);
}
