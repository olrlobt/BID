package com.ssafy.bid.domain.grade.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;
import com.ssafy.bid.domain.grade.service.CoreGradeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GradeApi {

	private final CoreGradeService coreGradeService;

	@GetMapping("/{gradeNo}/statistics")
	public ResponseEntity<GradeStatisticsFindResponse> findGradeStatistics(@PathVariable int gradeNo) {
		GradeStatisticsFindResponse response = coreGradeService.findGradeStatistics(gradeNo);
		return ResponseEntity.ok(response);
	}
}
