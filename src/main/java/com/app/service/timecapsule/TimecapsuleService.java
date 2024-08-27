package com.app.service.timecapsule;

import java.util.List;

import com.app.dto.timecapsule.Timecapsule;

	/*
	 * 타임캡슐(Timecapsule) 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
	 */
public interface TimecapsuleService {

    /**
     * 타임캡슐 정보를 저장합니다.
     *
     * @param tc 저장할 타임캡슐 정보가 담긴 {@link Timecapsule} 객체
     * @return 저장 성공 시 1 이상의 정수 값을 반환하고, 실패 시 0을 반환합니다.
     */
    public int saveTimecapsule(Timecapsule tc);

    /**
     * 데이터베이스에 저장된 모든 타임캡슐 정보를 조회합니다.
     *
     * @return 모든 타임캡슐 정보가 담긴 {@link List} 객체. 리스트가 비어있을 수 있으며, 타임캡슐이 존재하지 않으면 빈 리스트를 반환합니다.
     */
    public List<Timecapsule> selectAllTimecapsule();
}
