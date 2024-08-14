package com.app.service.file.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.File.FileDAO;
import com.app.dto.file.FileInfo;
import com.app.service.file.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	FileDAO fileDAO;
	
	@Override
	public int saveFileInfo(FileInfo fileInfo) {
		return fileDAO.saveFileInfo(fileInfo);
	}
	
	@Override
	public String findFileUrlByFileNameUserId(FileInfo fileInfo) {
		return fileDAO.findFileUrlByFileNameUserId(fileInfo);
	}

	@Override
	public int selectFileInfo(FileInfo fileInfo) {
		return fileDAO.selectFileInfo(fileInfo);
	}

	@Override
	public int updateFileInfo(FileInfo fileInfo) {
		return fileDAO.updateFileInfo(fileInfo);
	}

	@Override
	public String findFilePathByUserId(String userId) {
		String filePath = fileDAO.findFilePathByUserId(userId);
		return filePath;
	}
	
	
	
	

}
