package com.app.dto.file;

import lombok.Data;

@Data
public class FileInfo {
	
	String fileId; // 1, 2, 3, 4, 5 ~~~~ PK
	String userId;
	String fileName; //만들어진 파일명 (유니크ㅇㅇ 파일명 중복 방지) 
	String originFileName; //원래 파일명 (사용자가 업로드시 사용한 파일명)
	String filePath; //파일 저장된 경로
	String urlFilePath; //URL 요쳥시 경로

}
