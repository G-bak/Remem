package com.app.dto.accountBook;

import lombok.Data;

@Data
public class AccountBook {
	int accountBookId;  //pk
	String userId;		//유저 아이디
	String accountDate;		//날짜
	int salary;			//월급
	int sideJob;		//부업
	int saving; 		//저축
	int incomeTotal;	//수입 총 합계
	
	int foodExpenses;	//식비
	int traffic;		//교통
	int culture;		//문화
	int clothing; 		//의류
	int beauty;			//미용
	int telecom;		//통신
	int membershipFee;	//회비
	int dailyNecessity; //생필품
	int occasions; 		//경조사
	int spendingTotal;	//지출 총 합계
	
	int incomeSpendingTotal;	//수입&지출 총 합계

	
	
}

