package com.app.dto.diary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDiary {
	private String diaryId;
	private String userId;
	private String diaryTitle;
	private String writeDate;
	private String diaryContent;
	private List<String> keyword = new ArrayList<String>();
}
