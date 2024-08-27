package com.app.controller.friend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.common.APIResultCode;
import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.dto.friend.FriendDTO;
import com.app.dto.friend.FriendDiaryProfileDTO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;
import com.app.service.file.FileService;
import com.app.service.friend.FriendService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FriendController {

	@Autowired
	FriendService friendService;

	@Autowired
	FileService fileService;

	// 친구 검색
	@PostMapping("/searchFriend")
	@ResponseBody
	public ApiResponse<List<UserSearch>> searchFriend(@RequestBody SearchFriend searchFriend) {
		log.info("searchFriend 메서드 호출 - 검색 조건: {}", searchFriend);

		ApiResponse<List<UserSearch>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<UserSearch> searchedFriendList = null;

		try {
			if (searchFriend.getLoginUserId() != null && !searchFriend.getLoginUserId().isEmpty()) {
				log.debug("로그인 사용자 ID 확인 완료: {}", searchFriend.getLoginUserId());
				searchedFriendList = friendService.searchFriend(searchFriend);

				if (searchedFriendList != null && !searchedFriendList.isEmpty()) {
					log.info("검색된 친구 수: {}", searchedFriendList.size());
					for (UserSearch userSearch : searchedFriendList) {
						FriendStatusDTO friendStatusDTO = new FriendStatusDTO();
						friendStatusDTO.setLoginUserId(searchFriend.getLoginUserId());
						friendStatusDTO.setFriendId(userSearch.getUserId());

						userSearch.setFriend(friendService.checkIfFriendOrNot(friendStatusDTO));
						userSearch.setUrlFilePath(fileService.findFilePathByUserId(userSearch.getUserId()));
					}
					header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
					header.setResultMessage("친구 검색이 성공적으로 완료되었습니다.");
				} else {
					log.info("검색된 친구가 없음");
					header.setResultCode(APIResultCode.API_RESULT_NO_DATA);
					header.setResultMessage("검색된 친구가 없습니다.");
				}
			} else {
				log.warn("로그인된 사용자 ID가 제공되지 않음");
				header.setResultCode(APIResultCode.API_RESULT_MISSING_PARAMETER);
				header.setResultMessage("로그인된 사용자 ID가 제공되지 않았습니다.");
			}
		} catch (Exception e) {
			log.error("친구 검색 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("친구 검색 중 오류가 발생하였습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(searchedFriendList);
		return apiResponse;
	}

	// 친구 신청
	@PostMapping("/joinRequestFriend")
	@ResponseBody
	public ApiResponse<Integer> joinRequestFriend(@RequestBody FriendStatusDTO friendStatusDTO) {
		log.info("joinRequestFriend 메서드 호출 - 친구 상태 DTO: {}", friendStatusDTO);

		ApiResponse<Integer> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		int result = 0;

		try {
			result = friendService.joinRequestFriend(friendStatusDTO);

			if (result > 0) {
				log.info("친구 신청 성공 - 결과: {}", result);
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("친구 신청이 성공적으로 처리되었습니다.");
			} else {
				log.warn("친구 신청 실패");
				header.setResultCode(APIResultCode.API_RESULT_PROCESSING_ERROR);
				header.setResultMessage("친구 신청이 실패했습니다. 다시 시도해 주세요.");
			}

			apiResponse.setBody(result);
		} catch (Exception e) {
			log.error("친구 신청 처리 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("친구 신청 처리 중 오류가 발생했습니다.");
			apiResponse.setBody(result);
		}

		apiResponse.setHeader(header);
		return apiResponse;
	}

	// 친구 신청함 확인
	@PostMapping("/confirmRequestFriend")
	@ResponseBody
	public ApiResponse<List<User>> confirmRequestFriend(@RequestParam String loginUserId) {
		log.info("confirmRequestFriend 메서드 호출 - 로그인 사용자 ID: {}", loginUserId);

		ApiResponse<List<User>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<User> requestFriendList = null;

		try {
			requestFriendList = friendService.confirmRequestFriend(loginUserId);

			if (requestFriendList != null && !requestFriendList.isEmpty()) {
				log.info("친구 신청 목록 가져오기 성공 - 목록 수: {}", requestFriendList.size());
				for (User user : requestFriendList) {
					user.setUrlFilePath(fileService.findFilePathByUserId(user.getUserId()));
				}
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("친구 신청 목록을 성공적으로 가져왔습니다.");
			} else {
				log.info("친구 신청 목록이 없음");
				header.setResultCode(APIResultCode.API_RESULT_NO_DATA);
				header.setResultMessage("친구 신청 목록이 없습니다.");
			}

		} catch (Exception e) {
			log.error("친구 신청 목록 가져오는 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("친구 신청 목록을 가져오는 중 오류가 발생했습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(requestFriendList);
		return apiResponse;
	}

	// 친구 신청함에서 친구 신청 받기
	@PostMapping("/receiveFriendRequest")
	@ResponseBody
	public ApiResponse<List<User>> receiveFriendRequest(@RequestBody FriendStatusDTO friendStatusDTO) {
		log.info("receiveFriendRequest 메서드 호출 - 친구 상태 DTO: {}", friendStatusDTO);

		ApiResponse<List<User>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<User> requestFriendList = null;

		try {
			int deleteResult = friendService.deleteRequestFriend(friendStatusDTO);
			int insertResultOnWay = friendService.makeFriendsOneWay(friendStatusDTO);
			int insertResultTwoWay = friendService.makeFriendsTwoWay(friendStatusDTO);

			if (deleteResult > 0 && insertResultOnWay > 0 && insertResultTwoWay > 0) {
				log.info("친구 요청 처리 성공");
				requestFriendList = friendService.confirmRequestFriend(friendStatusDTO.getLoginUserId());

				if (requestFriendList != null && !requestFriendList.isEmpty()) {
					log.info("친구 목록 가져오기 성공 - 목록 수: {}", requestFriendList.size());
					for (User user : requestFriendList) {
						user.setUrlFilePath(fileService.findFilePathByUserId(user.getUserId()));
					}
					header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
					header.setResultMessage("친구 요청 처리 후, 가져올 친구 목록이 있습니다.");
				} else {
					log.info("친구 목록이 없음");
					header.setResultCode(APIResultCode.API_RESULT_NO_DATA);
					header.setResultMessage("친구 요청 처리 후, 가져올 친구 목록이 없습니다.");
				}
			} else {
				log.warn("친구 요청 처리 중 일부 단계 실패");
				header.setResultCode(APIResultCode.API_RESULT_PROCESSING_ERROR);
				header.setResultMessage("친구 요청 처리 중 일부 단계가 실패했습니다.");
			}
		} catch (Exception e) {
			log.error("친구 요청 처리 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("친구 요청 처리 중 오류가 발생했습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(requestFriendList);
		return apiResponse;
	}

	// 친구 추천리스트
	@PostMapping("/viewRecommendList")
	@ResponseBody
	public ApiResponse<List<User>> viewRecommendList(@RequestParam String loginUserId) {
		log.info("viewRecommendList 메서드 호출 - 로그인 사용자 ID: {}", loginUserId);

		ApiResponse<List<User>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<User> recommendList = null;

		try {
			recommendList = friendService.viewRecommendList(loginUserId);

			if (recommendList != null && !recommendList.isEmpty()) {
				log.info("친구 추천 리스트 가져오기 성공 - 추천 친구 수: {}", recommendList.size());
				for (User user : recommendList) {
					user.setUrlFilePath(fileService.findFilePathByUserId(user.getUserId()));
				}
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("친구 추천 리스트를 성공적으로 가져왔습니다.");
			} else {
				log.info("추천할 친구가 없음");
				header.setResultCode(APIResultCode.API_RESULT_NO_DATA);
				header.setResultMessage("추천할 친구가 없습니다.");
			}
		} catch (Exception e) {
			log.error("친구 추천 리스트 가져오는 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("친구 추천 리스트를 가져오는 중 오류가 발생했습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(recommendList);
		return apiResponse;
	}

	// 친구 일기 조회(친구들 정보 + 일기 정보 + 프로필 사진 주소)
	@PostMapping("/getFriendsDiaryTimeline")
	@ResponseBody
	public ApiResponse<List<FriendDiaryProfileDTO>> getFriendsDiaryTimeline(@RequestParam String loginUserId) {
		log.info("getFriendsDiaryTimeline 메서드 호출 - 로그인 사용자 ID: {}", loginUserId);

		ApiResponse<List<FriendDiaryProfileDTO>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<FriendDiaryProfileDTO> friendDiaryProfileList = null;

		try {
			friendDiaryProfileList = friendService.getFriendsDiaryTimeline(loginUserId);

			if (friendDiaryProfileList != null && !friendDiaryProfileList.isEmpty()) {
				log.info("친구들의 일기 타임라인 가져오기 성공 - 일기 수: {}", friendDiaryProfileList.size());
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("친구들의 일기 타임라인을 성공적으로 가져왔습니다.");
			} else {
				log.info("조회할 친구들의 일기가 없음");
				header.setResultCode(APIResultCode.API_RESULT_NO_DATA);
				header.setResultMessage("조회할 친구들의 일기가 없습니다.");
			}
		} catch (Exception e) {
			log.error("친구들의 일기 타임라인 가져오는 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("친구들의 일기 타임라인을 가져오는 중 오류가 발생했습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(friendDiaryProfileList);
		return apiResponse;
	}

	// 친구 목록 조회
	@PostMapping("/getFriendList")
	@ResponseBody
	public ApiResponse<List<FriendDTO>> getFriendList(@RequestParam String loginUserId) {
		log.info("getFriendList 메서드 호출 - 로그인 사용자 ID: {}", loginUserId);

		ApiResponse<List<FriendDTO>> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();
		List<FriendDTO> friendList = null;

		try {
			friendList = friendService.getFriendList(loginUserId);

			if (friendList != null && !friendList.isEmpty()) {
				log.info("친구 목록 가져오기 성공 - 친구 수: {}", friendList.size());
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("친구 목록을 성공적으로 가져왔습니다.");
			} else {
				log.info("친구 목록이 없음");
				header.setResultCode(APIResultCode.API_RESULT_NO_DATA);
				header.setResultMessage("친구 목록이 없습니다.");
			}
		} catch (Exception e) {
			log.error("친구 목록 가져오는 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("친구 목록을 가져오는 중 오류가 발생했습니다.");
		}

		apiResponse.setHeader(header);
		apiResponse.setBody(friendList);
		return apiResponse;
	}

	// 친구 언팔로우(친구 삭제)
	@PostMapping("/unfollowFriend")
	@ResponseBody
	public ApiResponse<String> unfollowFriend(@RequestBody FriendStatusDTO friendStatusDTO) {
		log.info("unfollowFriend 메서드 호출 - 친구 상태 DTO: {}", friendStatusDTO);

		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiResponseHeader header = new ApiResponseHeader();

		try {
			int unfollowResultOneWay = friendService.unfollowFriendOneWay(friendStatusDTO);
			int unfollowResultTwoWay = friendService.unfollowFriendTwoWay(friendStatusDTO);

			if (unfollowResultOneWay > 0 && unfollowResultTwoWay > 0) {
				log.info("언팔로우 성공");
				header.setResultCode(APIResultCode.API_RESULT_SUCCESS);
				header.setResultMessage("언팔로우가 성공적으로 완료되었습니다.");
				apiResponse.setBody("언팔로우가 완료되었습니다.");
			} else {
				log.warn("언팔로우 실패");
				header.setResultCode(APIResultCode.API_RESULT_PROCESSING_ERROR);
				header.setResultMessage("언팔로우에 실패했습니다.");
				apiResponse.setBody("언팔로우 실패");
			}
		} catch (Exception e) {
			log.error("언팔로우 처리 중 오류 발생", e);
			header.setResultCode(APIResultCode.API_RESULT_FAILURE);
			header.setResultMessage("언팔로우 처리 중 오류가 발생했습니다.");
			apiResponse.setBody("언팔로우 실패");
		}

		apiResponse.setHeader(header);
		return apiResponse;
	}

}
