package com.app.controller.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
import com.app.dto.user.User;
import com.app.service.file.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FileController {
	
	@Autowired
	FileService fileservice;
	
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
                	String fileName = Paths.get(item.getName()).getFileName().toString(); // 원본 파일 이름
                	
                	
                	System.out.println(fileName);
                	
                	  // 파일 이름 유효성 검사~~~~
                    if (fileName == null || fileName.trim().isEmpty() ) {
                        throw new IllegalArgumentException("Invalid file name: " + fileName);
                    }
                    
                    
                    
                	
//                	int dotIndex = fileName.lastIndexOf('.');
//                	if (dotIndex != -1) {
//                	    String fileExtension = fileName.substring(dotIndex);
//                	    // 추가 코드
//                	} else {
//                	    // 확장자가 없는 경우 처리 (예: 파일 확장자가 없다고 알림)
//                	    throw new IllegalArgumentException("Invalid file name: " + fileName);
//                	}
                	
                	//파일 확장자 검사 (필요시)
                    String fileExtension = fileName.substring(fileName.lastIndexOf('.')); // 파일 확장자 추출
                    
                    if (fileExtension == null || fileExtension.trim().isEmpty()) {
                        throw new IllegalArgumentException("File extension is missing in file name: " + fileName);
                    }
                    
                    
                    String uuid = UUID.randomUUID().toString(); // UUID 생성
                    String newFileName = uuid + fileExtension; // 새 파일 이름
                    
                    
                    System.out.println("Original File Name: " + fileName);
                    System.out.println("New File Name: " + newFileName); // 수정된 파일 이름 출력
                
                        
                    File uploadDir = new File(UPLOAD_DIRECTORY);

                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    File file = new File(uploadDir, newFileName);
                    item.write(file);

                    // 여기서부터 -------------------------------
                    
                    // 파일의 URL 생성
                    String fileUrl = "/uploads/" + newFileName;
                    System.out.println("File URL: " + fileUrl);
                    //model.addAttribute("absolutePath", fileUrl);
                    
                    // DB
                    FileInfo fileinfo = new FileInfo();
                    User user = (User) session.getAttribute("user");
                    System.out.println("세션 fileinfo: " + user);
                    fileinfo.setUserId(user.getUserId()); //세션에서 id 받아오기 처리 !!!!!!!!!!!!!!(수정)
                    fileinfo.setFileName(newFileName);
                    fileinfo.setUrlFilePath(fileUrl);
                    
                    
                    
                    //DB에 fileinfo 테이블에 user1이 없으면 insert
                    //user1이 있으면 변경된 사진으로 update
                    int selectResult = fileservice.selectFileInfo(fileinfo);
                    System.out.println("count: " + selectResult);
                    
                    if(selectResult > 0) {
                    	int result = fileservice.updateFileInfo(fileinfo);
                    	if (result > 0) {
                         	System.out.println("성공");
                         } else {
                         	System.out.println("실패");
                         }
                    	
                    	
                    } else {
                    	 int result = fileservice.saveFileInfo(fileinfo);
                         if (result > 0) {
                         	System.out.println("성공");
                         } else {
                         	System.out.println("실패");
                         }
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


