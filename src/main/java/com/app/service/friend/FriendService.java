package com.app.service.friend;

import java.util.List;

import com.app.dto.friend.FriendDTO;
import com.app.dto.friend.FriendDiaryProfileDTO;
import com.app.dto.friend.FriendStatusDTO;
import com.app.dto.friend.SearchFriend;
import com.app.dto.friend.UserSearch;
import com.app.dto.user.User;

/**
 * FriendService 인터페이스는 친구 관리와 관련된 여러 기능을 제공합니다.
 * 사용자 검색, 친구 신청, 친구 관계 관리 등의 메서드를 포함합니다.
 */
public interface FriendService {

    /**
     * 사용자의 입력으로 친구를 검색합니다.
     *
     * @param searchFriend 검색 조건을 포함한 객체
     * @return 검색된 사용자의 목록
     */
    public List<UserSearch> searchFriend(SearchFriend searchFriend);

    /**
     * 특정 사용자가 친구인지 여부를 확인합니다.
     *
     * @param friendStatusDTO 친구 관계 확인을 위한 정보가 포함된 객체
     * @return 친구 관계가 존재하면 true, 그렇지 않으면 false
     */
    public boolean checkIfFriendOrNot(FriendStatusDTO friendStatusDTO);

    /**
     * 친구 신청을 보냅니다.
     *
     * @param friendStatusDTO 친구 신청 정보를 포함한 객체
     * @return 성공적으로 친구 신청이 이루어지면 1, 그렇지 않으면 0
     */
    public int joinRequestFriend(FriendStatusDTO friendStatusDTO);

    /**
     * 로그인한 사용자가 받은 친구 신청을 확인합니다.
     *
     * @param loginUserId 로그인한 사용자의 ID
     * @return 친구 신청을 보낸 사용자의 목록
     */
    public List<User> confirmRequestFriend(String loginUserId);

    /**
     * 친구 신청을 수락한 후 친구 신청 테이블에서 해당 요청을 삭제합니다.
     *
     * @param friendStatusDTO 친구 신청 수락 정보를 포함한 객체
     * @return 성공적으로 삭제되면 1, 그렇지 않으면 0
     */
    public int deleteRequestFriend(FriendStatusDTO friendStatusDTO);

    /**
     * 친구 신청을 수락한 후 사용자의 정보를 friendships 테이블에 추가합니다.
     *
     * @param friendStatusDTO 친구 관계 생성을 위한 정보가 포함된 객체
     * @return 성공적으로 추가되면 1, 그렇지 않으면 0
     */
    public int makeFriendsOneWay(FriendStatusDTO friendStatusDTO);

    /**
     * 친구 신청을 수락한 후 친구의 정보를 friendships 테이블에 추가합니다.
     *
     * @param friendStatusDTO 친구 관계 생성을 위한 정보가 포함된 객체
     * @return 성공적으로 추가되면 1, 그렇지 않으면 0
     */
    public int makeFriendsTwoWay(FriendStatusDTO friendStatusDTO);

    /**
     * 로그인한 사용자를 위한 친구 추천 리스트를 조회합니다.
     *
     * @param loginUserId 로그인한 사용자의 ID
     * @return 친구 추천 리스트
     */
    public List<User> viewRecommendList(String loginUserId);

    /**
     * 특정 사용자의 팔로워 수를 계산합니다.
     *
     * @param userId 사용자 ID
     * @return 팔로워 수
     */
    public int countFollower(String userId);

    /**
     * 특정 사용자의 팔로잉 수를 계산합니다.
     *
     * @param userId 사용자 ID
     * @return 팔로잉 수
     */
    public int countFollowing(String userId);

    /**
     * 친구의 일기 타임라인을 조회합니다.
     *
     * @param loginUserId 로그인한 사용자의 ID
     * @return 친구 일기 목록
     */
    public List<FriendDiaryProfileDTO> getFriendsDiaryTimeline(String loginUserId);

    /**
     * 친구 목록을 조회합니다.
     *
     * @param loginUserId 로그인한 사용자의 ID
     * @return 친구 목록
     */
    public List<FriendDTO> getFriendList(String loginUserId);

    /**
     * 친구를 언팔로우할 때 friendships 테이블에서 사용자 -> 친구 관계를 삭제합니다.
     *
     * @param friendStatusDTO 친구 삭제 정보를 포함한 객체
     * @return 성공적으로 삭제되면 1, 그렇지 않으면 0
     */
    public int unfollowFriendOneWay(FriendStatusDTO friendStatusDTO);

    /**
     * 친구를 언팔로우할 때 friendships 테이블에서 친구 -> 사용자 관계를 삭제합니다.
     *
     * @param friendStatusDTO 친구 삭제 정보를 포함한 객체
     * @return 성공적으로 삭제되면 1, 그렇지 않으면 0
     */
    public int unfollowFriendTwoWay(FriendStatusDTO friendStatusDTO);
}
