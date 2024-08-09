package com.app.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.user.User;
import com.app.service.user.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@GetMapping("/main")
	public String home(HttpSession session, Model model) {
		session.setAttribute("loginUserId", "user1");
		
		model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
		
		System.out.println(session.getAttribute("loginUserId"));
		
		return "main";
	}
	
	
	
	@GetMapping("/signup")
	public String signup() {
		return "user/signup";
	}

	@PostMapping("/signup")
	public String signupAction(@RequestParam HashMap<String, String> paramMap) {

		User user = new User();
		user.setUserId(paramMap.get("userId"));
		user.setUserName(paramMap.get("userName"));
		user.setUserAddress(paramMap.get("userAddress"));

		//System.out.println(user);

		return "";
	}
	
	
	


}
