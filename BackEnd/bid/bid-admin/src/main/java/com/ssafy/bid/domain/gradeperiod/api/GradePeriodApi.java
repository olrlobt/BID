package com.ssafy.bid.domain.gradeperiod.api;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodModifyRequest;
import com.ssafy.bid.domain.gradeperiod.service.GradePeriodService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GradePeriodApi {

	private final GradePeriodService gradePeriodService;

	@PatchMapping("{gradeNo}/periods")
	public void modifyGradePeriods(@PathVariable int gradeNo, GradePeriodModifyRequest gradePeriodModifyRequest) {
		gradePeriodService.modifyGradePeriod(gradeNo, gradePeriodModifyRequest);
	}
}
