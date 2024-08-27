package com.app.service.user;

import java.util.HashMap;
import java.util.List;

import com.app.dto.user.User;

public interface UserService {

	/**
	 * 사용자 정보를 저장.
	 *
	 * @param user 저장할 사용자 정보를 담고 있는 User 객체.
	 * @return 저장이 성공적으로 이루어지면 1, 그렇지 않으면 0.
	 */
	public int saveUser(User user);

	/**
	 * 사용자 로그인.
	 *
	 * @param user 로그인할 사용자 정보를 담고 있는 User 객체.
	 * @return 로그인된 사용자 정보를 담고 있는 User 객체.
	 */
	public User loginUser(User user);

	/**
	 * 사용자 삭제.
	 *
	 * @param userId 삭제할 사용자의 ID.
	 * @return 삭제가 성공적으로 이루어지면 1, 그렇지 않으면 0.
	 */
	public int removeUser(String userId);

	/**
	 * 사용자 ID로 사용자 정보를 조회.
	 *
	 * @param userId 조회할 사용자의 ID.
	 * @return 조회된 사용자 정보를 담고 있는 User 객체.
	 */
	public User findUserById(String userId);

	/**
	 * 사용자 주소 수정.
	 *
	 * @param user 수정할 주소 정보를 담고 있는 User 객체.
	 * @return 주소 수정이 성공적으로 이루어지면 1, 그렇지 않으면 0.
	 */
	public int modifyAddress(User user);

	/**
	 * 사용자 비밀번호 수정.
	 *
	 * @param user 수정할 비밀번호 정보를 담고 있는 User 객체.
	 * @return 비밀번호 수정이 성공적으로 이루어지면 1, 그렇지 않으면 0.
	 */
	public int modifyPassword(User user);

	/**
	 * 중복된 ID 확인.
	 *
	 * @param signupId 중복 확인을 할 ID.
	 * @return 중복된 ID가 존재하면 1, 그렇지 않으면 0.
	 */
	public int checkDuplicatedId(String signupId);

}
