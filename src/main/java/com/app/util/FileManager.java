package com.app.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.file.FileInfo;

public class FileManager {
	
	private static final String FILE_DIRECTORY_PATH = "d:/fileStorage/image/"; //파일이 저장될 위치
	private static final String FILE_URL_PATH = "/fileStorage/image/";
	
	public static FileInfo storeFile(MultipartFile file) throws IllegalStateException, IOException {
		
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setFileName(file.getOriginalFilename());	//image01.png
//		fileInfo.setFileSize(file.getSize());
		fileInfo.setFilePath(FILE_DIRECTORY_PATH);
		fileInfo.setUrlFilePath(FILE_URL_PATH);
		
		String extension = extractExtension(file.getOriginalFilename());
//		fileInfo.setFileExtension(extension);

		//extension 확인 	png jpg jpeg ->    FILE_DIRECTORY_PATH + "image/
		//extension 확인 	hwp docx ppt xls ->    FILE_DIRECTORY_PATH + "document/
		
		String fileName = createFileName(extension); //파일명 조정
		fileInfo.setFileName(fileName);
		
		//실제 파일 저장!
		file.transferTo(new File(fileInfo.getFilePath() + fileInfo.getFileName()));
		//d:/fileStorage/4553rfwafio34bdf-4034-b32f-56623dac2aaa.png
		//file.transferTo(new File());
		
		return fileInfo;
	}
	
	private static String createFileName(String extension) {
		String fileName = UUID.randomUUID().toString() + "." + extension;
		// 4553rfwafio34bdf-4034-b32f-56623dac2aaa . png
		return fileName;
	}
	
	private static String extractExtension(String fileName) {
		//확장자만 추출해서 반환
		// asdfo.png  osdfijiod.hwp  asodifjisa.jpg
		int dotIndex = fileName.lastIndexOf(".");
		String extension = fileName.substring(dotIndex+1);
		//   image01.png	png		hi.hwp	hwp		*.png 
		return extension;
	}

}
