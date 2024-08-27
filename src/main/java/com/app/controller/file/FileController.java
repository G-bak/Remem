package com.app.controller.file;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.dto.file.FileInfo;
import com.app.dto.user.User;
import com.app.service.file.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FileController {

	@Autowired
	FileService fileservice; // FileService 주입

	private static final String UPLOAD_DIRECTORY = "d:/uploads"; // 파일이 업로드될 디렉토리 경로

	@PostMapping("/upload")
	public String handleFileUpload(HttpServletRequest request, HttpSession session,
			RedirectAttributes redirectAttributes) {
		log.info("파일 업로드 요청 처리 시작 - 세션 ID: {}", session.getId());

		// 파일 업로드 요청인지 확인
		if (!ServletFileUpload.isMultipartContent(request)) {
			log.warn("파일 업로드 요청이 아님");
			redirectAttributes.addFlashAttribute("fileErrorMessage", "파일 업로드 요청이 아닙니다.");
			return "redirect:/main";
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 요청에서 파일 항목들을 파싱
			List<FileItem> items = upload.parseRequest(request);
			log.info("업로드된 파일 개수: {}", items.size());

			for (FileItem item : items) {
				if (!item.isFormField()) {
					String fileName = Paths.get(item.getName()).getFileName().toString(); // 원본 파일 이름 가져오기
					log.info("파일 이름: {}", fileName);

					// 파일 이름 유효성 검사
					if (fileName == null || fileName.trim().isEmpty()) {
						log.warn("잘못된 파일 이름");
						redirectAttributes.addFlashAttribute("fileErrorMessage", "잘못된 파일 이름입니다.");
						return "redirect:/main";
					}

					// 파일 확장자 추출
					String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
					log.info("파일 확장자: {}", fileExtension);

					// 파일 확장자 유효성 검사
					if (fileExtension == null || fileExtension.trim().isEmpty()) {
						log.warn("파일 확장자가 없음");
						redirectAttributes.addFlashAttribute("fileErrorMessage", "파일 확장자가 없습니다.");
						return "redirect:/main";
					}

					String uuid = UUID.randomUUID().toString(); // UUID 생성
					String newFileName = uuid + fileExtension; // 새 파일 이름 생성
					log.info("새 파일 이름: {}", newFileName);

					// 업로드 디렉토리가 존재하지 않으면 생성
					File uploadDir = new File(UPLOAD_DIRECTORY);
					if (!uploadDir.exists()) {
						log.info("업로드 디렉토리가 존재하지 않음, 새로 생성: {}", UPLOAD_DIRECTORY);
						uploadDir.mkdirs();
					}

					// 새 파일 경로 생성 후 파일 저장
					File file = new File(uploadDir, newFileName);
					item.write(file);
					log.info("파일 저장 성공: {}", file.getAbsolutePath());

					// 파일의 URL 생성
					String fileUrl = "/uploads/" + newFileName;

					// 파일 정보를 FileInfo 객체에 담기
					FileInfo fileinfo = new FileInfo();
					User user = (User) session.getAttribute("user"); // 세션에서 사용자 정보 가져오기
					fileinfo.setUserId(user.getUserId());
					fileinfo.setFileName(newFileName);
					fileinfo.setUrlFilePath(fileUrl);

					// DB에 프로필 사진이 이미 존재하는지 확인
					int selectResult = fileservice.selectFileInfo(fileinfo);
					log.info("프로필 사진 존재 여부: {}", selectResult > 0 ? "기존 프로필 있음" : "새 프로필 설정");

					if (selectResult > 0) {
						// 기존 프로필 사진이 있으면 업데이트
						int result = fileservice.updateFileInfo(fileinfo);
						if (result > 0) {
							log.info("프로필 사진 업데이트 성공");
							redirectAttributes.addFlashAttribute("fileErrorMessage", "프로필 사진 변경 완료 !");
						} else {
							log.warn("프로필 사진 업데이트 실패");
							redirectAttributes.addFlashAttribute("fileErrorMessage", "프로필 사진 변경 실패");
						}
					} else {
						// 기존 프로필 사진이 없으면 새로 저장
						int result = fileservice.saveFileInfo(fileinfo);
						if (result > 0) {
							log.info("프로필 사진 저장 성공");
							redirectAttributes.addFlashAttribute("fileErrorMessage", "프로필 사진 설정 완료 !");
						} else {
							log.warn("프로필 사진 저장 실패");
							redirectAttributes.addFlashAttribute("fileErrorMessage", "프로필 사진 설정 실패");
						}
					}

					// DB에서 파일 URL을 가져와 세션에 저장
					String filePath = fileservice.findFileUrlByFileNameUserId(fileinfo);
					if (filePath != null) {
						session.setAttribute("filePath", filePath);
						log.info("파일 경로 세션에 저장: {}", filePath);
					}
				}
			}
		} catch (Exception e) {
			// 파일 업로드 중 예외가 발생하면 예외 메시지를 리다이렉트 시 전달
			log.error("프로필 사진 업로드 중 오류 발생", e);
			redirectAttributes.addFlashAttribute("fileErrorMessage", "프로필 사진 업로드 중 오류가 발생했습니다.");
		}

		// 메인 페이지로 리다이렉트
		return "redirect:/main";
	}
}
