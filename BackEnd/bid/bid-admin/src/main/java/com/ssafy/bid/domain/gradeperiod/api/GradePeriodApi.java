package com.ssafy.bid.domain.gradeperiod.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodListUpdateRequest;
import com.ssafy.bid.domain.gradeperiod.service.GradePeriodService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class GradePeriodApi {

	private final GradePeriodService gradePeriodService;

	@PatchMapping("{gradeNo}/grade-periods")
	public ResponseEntity<?> modifyGradePeriods(@PathVariable int gradeNo,
		@RequestBody GradePeriodListUpdateRequest gradePeriodListUpdateRequest) {
		gradePeriodService.updateGradePeriod(gradeNo, gradePeriodListUpdateRequest);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
