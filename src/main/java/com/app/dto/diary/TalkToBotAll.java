package com.app.dto.diary;

import lombok.Data;

@Data
public class TalkToBotAll {
	private String userId;
	private int roomIndex;
	private String dataId;
	private String questionHtml;
	private String chatHtml;
}
