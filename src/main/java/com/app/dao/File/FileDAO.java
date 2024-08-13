package com.app.dao.File;

import com.app.dto.file.FileInfo;

public interface FileDAO {
	
	public int saveFileInfo(FileInfo fileInfo);
	
	public String findFileUrlByFileNameUserId(FileInfo fileInfo);
	
	public int selectFileInfo(FileInfo fileInfo);
	
	public int updateFileInfo(FileInfo fileInfo);
}


