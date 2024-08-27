package com.app.controller.timecapsule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dto.api.ApiResponse;
import com.app.dto.api.ApiResponseHeader;
import com.app.dto.timecapsule.Timecapsule;
import com.app.service.timecapsule.TimecapsuleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TimecapsuleController {

    @Autowired
    TimecapsuleService timecapsuleService;

    // 타임캡슐 저장
    @ResponseBody
    @PostMapping("/save/Timecapsule")
    public ApiResponse<Timecapsule> saveTimecapsule(@RequestBody Timecapsule tc) {
        ApiResponse<Timecapsule> apiResponse = new ApiResponse<>();
        ApiResponseHeader apiHeader = new ApiResponseHeader();

        try {
            if (tc == null) {
                apiHeader.setResultCode("99");
                apiHeader.setResultMessage("Invalid Timecapsule object");
                apiResponse.setHeader(apiHeader);
                log.warn("Invalid Timecapsule object received.");
                return apiResponse;
            }

            int saveResult = timecapsuleService.saveTimecapsule(tc);

            if (saveResult > 0) {
                apiHeader.setResultCode("00");
                apiHeader.setResultMessage("Timecapsule saved successfully");
                apiResponse.setBody(tc);
                log.info("Timecapsule saved successfully: {}", tc);
            } else {
                apiHeader.setResultCode("99");
                apiHeader.setResultMessage("Failed to save Timecapsule");
                log.warn("Failed to save Timecapsule: {}", tc);
            }

        } catch (Exception e) {
            apiHeader.setResultCode("99");
            apiHeader.setResultMessage("Error occurred while saving Timecapsule: " + e.getMessage());
            apiResponse.setBody(null);
            log.error("Error occurred while saving Timecapsule", e);
        }

        apiResponse.setHeader(apiHeader);
        return apiResponse;
    }

    // 타임캡슐 전체 조회
    @ResponseBody
    @GetMapping("/all/Timecapsules")
    public ApiResponse<List<Timecapsule>> getAllTimecapsules() {
        ApiResponse<List<Timecapsule>> apiResponse = new ApiResponse<>();
        ApiResponseHeader apiHeader = new ApiResponseHeader();

        try {
            List<Timecapsule> tcList = timecapsuleService.selectAllTimecapsule();

            if (tcList != null && !tcList.isEmpty()) {
                apiHeader.setResultCode("00");
                apiHeader.setResultMessage("Timecapsules retrieved successfully");
                apiResponse.setBody(tcList);
                log.info("Timecapsules retrieved successfully: {} items", tcList.size());
            } else {
                apiHeader.setResultCode("99");
                apiHeader.setResultMessage("No Timecapsules found");
                log.info("No Timecapsules found.");
            }

        } catch (Exception e) {
            apiHeader.setResultCode("99");
            apiHeader.setResultMessage("Error occurred while retrieving Timecapsules: " + e.getMessage());
            apiResponse.setBody(null);
            log.error("Error occurred while retrieving Timecapsules", e);
        }

        apiResponse.setHeader(apiHeader);
        return apiResponse;
    }
}
