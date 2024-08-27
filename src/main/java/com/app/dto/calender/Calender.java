package com.app.dto.calender;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Calender {
	private String calenderDate;
	private String dataId;
	private String userId;
	private String calenderTitle;
	private List<String> friendIdList = new ArrayList<String>();
	private List<String> friendNameList = new ArrayList<String>();
	private String hasFriends;
	private String hasDiary;
}
