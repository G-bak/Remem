package com.app.dto.diary;

import lombok.Data;

@Data
public class TalkToBotData {
	private String dataId;
	private String userId;
	private int roomIndex;
	private int messageIndex;
	private String botAnswer;
	private String userQuestion;	
}