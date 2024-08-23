package com.app.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.file.FileInfo;

public class FileManager {

	private static final String FILE_DIRECTORY_PATH = "d:/fileStorage/image/"; // 파일이 저장될 위치
	private static final String FILE_URL_PATH = "/fileStorage/image/";

	// 파일을 저장하고 파일 정보를 반환
	public static FileInfo storeFile(MultipartFile file) throws IllegalStateException, IOException {

		FileInfo fileInfo = new FileInfo();

		fileInfo.setFileName(file.getOriginalFilename()); // 원본 파일명 설정

		fileInfo.setFilePath(FILE_DIRECTORY_PATH); // 파일 저장 경로 설정
		fileInfo.setUrlFilePath(FILE_URL_PATH); // 파일 접근 URL 설정

		String extension = extractExtension(file.getOriginalFilename()); // 파일 확장자 추출
		String fileName = createFileName(extension); // 새 파일명 생성
		fileInfo.setFileName(fileName);

		// 실제 파일 저장
		file.transferTo(new File(fileInfo.getFilePath() + fileInfo.getFileName()));

		return fileInfo;
	}

	// UUID를 이용하여 고유한 파일명을 생성
	private static String createFileName(String extension) {
		String fileName = UUID.randomUUID().toString() + "." + extension;
		return fileName;
	}

	// 파일명에서 확장자를 추출
	private static String extractExtension(String fileName) {
		// 확장자만 추출하여 반환
		int dotIndex = fileName.lastIndexOf(".");
		String extension = fileName.substring(dotIndex + 1);
		return extension;
	}
}
