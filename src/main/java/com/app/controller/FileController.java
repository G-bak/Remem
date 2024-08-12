package com.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.dto.file.FileInfo;
import com.app.service.file.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FileController {
	
	@Autowired
	FileService fileservice;
	
    //private static final String UPLOAD_DIRECTORY = "src/main/resources/static/uploads";
	private static final String UPLOAD_DIRECTORY = "d:/uploads";

    @RequestMapping("/upload")
    public String showUploadForm() {
        return "uploadForm"; // 업로드 폼을 포함한 JSP 파일 이름
    }

    @PostMapping("/upload")
    public String handleFileUpload(HttpServletRequest request, Model model, HttpSession session) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            model.addAttribute("message", "파일 업로드 요청이 아닙니다.");
            return "uploadForm";
        }

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> items = upload.parseRequest(request);
            
            for (FileItem item : items) {
                if (!item.isFormField()) {
                	System.out.println("Item: " + item);
                    String fileName = Paths.get(item.getName()).getFileName().toString(); // 파일 이름 가져오기
                    System.out.println("File Name: " + fileName);
                    File uploadDir = new File(UPLOAD_DIRECTORY);

                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    File file = new File(uploadDir, fileName);
                    item.write(file);

                    // 여기서부터 -------------------------------
                    
                    // 파일의 URL 생성
                    String fileUrl = "/uploads/" + fileName;
                    System.out.println("File URL: " + fileUrl);
                    model.addAttribute("absolutePath", fileUrl);
                    
                    // DB
                    FileInfo fileinfo = new FileInfo();
                    fileinfo.setUserId("user1");
                    fileinfo.setFileName(fileName);
                    fileinfo.setUrlFilePath(fileUrl);
                    
                    // 파일 URL 데이터를 DB에 저장시키는 DAO 메서드 호출
                    int result = fileservice.saveFileInfo(fileinfo);
                    if (result > 0) {
                    	System.out.println("성공");
                    } else {
                    	System.out.println("실패");
                    }
                    
                    
                    // 파일 URL 데이터를 DB에 불러오는 DAO 메서드 호출
                    String filePath = null;
                    
                    filePath = fileservice.findFileUrlByFileNameUserId(fileinfo);
                    
                    if (filePath != null) {
                    	System.out.println("성공");
                    	session.setAttribute("filePath", filePath);
                    	// 콘솔창 테스트 코드
                    	String filePath11 = session.getAttribute("filePath").toString();
                    	System.out.println(filePath11);
                    } else {
                    	System.out.println("실패");
                    }
                    
                    
                 // 여기까지 -------------------------------
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "파일 업로드 중 오류가 발생했습니다.");
        }

        return "redirect:/main"; // 업로드 폼을 포함한 JSP 파일 이름
    }
    
//    public void test(HttpSession session) {
//    	session.getAttribute("filePath").toString();
//    }
    
}


