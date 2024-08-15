package com.app.dto.calender;

import lombok.Data;

@Data
public class CalenderMemoDiary {
	private String dataId;
	private String readerId;
	private String appointmentTime;
	private String memoContent;
	private String diaryTitle;
	private String diaryContent;
}
