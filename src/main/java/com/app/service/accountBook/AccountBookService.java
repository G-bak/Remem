package com.app.service.accountBook;

import com.app.dto.accountBook.AccountBook;
import com.app.dto.accountBook.AccountBookSearch;

	/*
	 * 가계부(AccountBook) 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
	 */
public interface AccountBookService {

    /**
     * 가계부를 조회합니다.
     *
     * @param abs 가계부를 조회하기 위한 검색 조건을 담고 있는 {@link AccountBookSearch} 객체
     * @return 조회된 가계부 정보가 담긴 {@link AccountBook} 객체. 가계부가 존재하지 않으면 null을 반환할 수 있음.
     */
    public AccountBook viewAccountBook(AccountBookSearch abs);

    /**
     * 새로운 가계부 정보를 저장합니다.
     *
     * @param accountBook 저장할 가계부 정보가 담긴 {@link AccountBook} 객체
     * @return 저장 성공 시 1 이상의 정수 값을 반환하고, 실패 시 0을 반환합니다.
     */
    public int saveAccountBook(AccountBook accountBook);

    /**
     * 기존 가계부 정보를 수정합니다.
     *
     * @param accountBook 수정할 가계부 정보가 담긴 {@link AccountBook} 객체
     * @return 수정 성공 시 1 이상의 정수 값을 반환하고, 실패 시 0을 반환합니다.
     */
    public int modifyAccountBook(AccountBook accountBook);
}
