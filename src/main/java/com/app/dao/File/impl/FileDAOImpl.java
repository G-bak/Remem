package com.app.dao.File.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.File.FileDAO;
import com.app.dto.file.FileInfo;

@Repository
public class FileDAOImpl implements FileDAO {

	@Autowired				
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int saveFileInfo(FileInfo fileInfo) {
		return sqlSessionTemplate.insert("file_mapper.saveFileInfo", fileInfo);
	}

	@Override
	public String findFileUrlByFileNameUserId(FileInfo fileInfo) {
		return sqlSessionTemplate.selectOne("file_mapper.findFileUrlByFileNameUserId", fileInfo);
	}

}
