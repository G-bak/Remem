package com.app.dto.api;

import java.util.List;

import lombok.Data;

@Data
public class HobbyInfo {
	
	String name;
	String hobby;
	int countPerWeek;
	
	List<Hobby> hobbyList;
}
