//package com.app.interceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class LoginInterceptor implements HandlerInterceptor {
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		HttpSession session = request.getSession(); 
//		// "user"는 세션에 저장된 로그인 사용자 정보라고 가정합니다.
//		if (session.getAttribute("user") == null) {
//			// 로그인하지 않은 경우 로그인 페이지로 리다이렉트합니다.
//			response.sendRedirect("/startpage");
//			return false; // 컨트롤러로 요청을 보내지 않음
//		}
//		return true; // 로그인된 경우 요청을 계속 진행
//	}
//}
