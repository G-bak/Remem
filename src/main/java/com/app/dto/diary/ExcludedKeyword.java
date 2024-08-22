package com.app.dto.diary;

import lombok.Data;

@Data
public class ExcludedKeyword {
	private String userId;
	private String roomId;
	private String dataIndex;
	private String dataId;
	private String dataKey;
	private int messageIndex;
	private String excludedKeyword;
	private String UserMessage;
	private String BotMessage;
}
