package com.app.controller.timecapsule;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.timecapsule.Timecapsule;
import com.app.dto.timecapsule.TimecapsuleSearch;
import com.app.service.timecapsule.TimecapsuleService;

@Controller
public class TimecapsuleController {

	@Autowired
	TimecapsuleService timecapsuleService;

	// 타임캡슐 저장
	@ResponseBody
	@PostMapping("/save/Timecapsule")
	public Timecapsule saveTimecapsule(@RequestBody Timecapsule tc) {
		System.out.println(tc);
		int saveTimecapsule = timecapsuleService.saveTimecapsule(tc);
		if (saveTimecapsule > 0) {
			return tc;
		} else {
			return null;
		}
	}

	// 타임캡슐 전체 조회
	@ResponseBody
	@GetMapping("/all/Timecapsules")
	public List<Timecapsule> getAllTimecapsules() {
		List<Timecapsule> tcList = timecapsuleService.selectAllTimecapsule();
		System.out.println(tcList);
		return tcList;
	}
}
