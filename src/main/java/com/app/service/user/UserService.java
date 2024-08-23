package com.app.service.user;

import java.util.HashMap;
import java.util.List;

import com.app.dto.user.User;

public interface UserService {

	// 사용자 정보 저장
	public int saveUser(User user);

	// 사용자 로그인
	public User loginUser(User user);

	// 사용자 삭제
	public int removeUser(String userId);

	// 사용자 ID로 사용자 조회
	public User findUserById(String userId);

	// 사용자 주소 수정
	public int modifyAddress(User user);

	// 사용자 비밀번호 수정
	public int modifyPassword(User user);

	// 중복된 ID 확인
	public int checkDuplicatedId(String signupId);

}
