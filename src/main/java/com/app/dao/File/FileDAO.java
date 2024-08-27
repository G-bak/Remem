package com.app.dao.File;

import com.app.dto.file.FileInfo;
import com.app.dto.friend.FriendStatusDTO;

public interface FileDAO {

	/**
	 * 프로필 사진을 저장합니다.
	 *
	 * @param fileInfo 저장할 파일 정보 객체
	 * @return 저장 성공 시 1, 실패 시 0
	 */
	public int saveFileInfo(FileInfo fileInfo);

	/**
	 * userId로 프로필 URL을 조회합니다.
	 *
	 * @param fileInfo 조회할 파일 정보 객체 (fileName과 userId 포함)
	 * @return 파일의 URL 경로 문자열
	 */
	public String findFileUrlByFileNameUserId(FileInfo fileInfo);

	/**
	 * 저장된 프로필 사진이 있는지 조회합니다.
	 *
	 * @param fileInfo 확인할 파일 정보 객체
	 * @return 프로필 사진이 있으면 1, 없으면 0
	 */
	public int selectFileInfo(FileInfo fileInfo);

	/**
	 * 저장된 프로필 사진이 있다면 프로필 사진을 업데이트합니다.
	 *
	 * @param fileInfo 업데이트할 파일 정보 객체
	 * @return 업데이트 성공 시 1, 실패 시 0
	 */
	public int updateFileInfo(FileInfo fileInfo);

	/**
	 * userId로 파일 경로를 조회합니다.
	 *
	 * @param userId 파일 경로를 조회할 사용자 ID
	 * @return 파일 경로 문자열
	 */
	public String findFilePathByUserId(String userId);
}
