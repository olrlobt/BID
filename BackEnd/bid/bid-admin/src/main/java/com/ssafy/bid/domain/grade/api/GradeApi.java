package com.ssafy.bid.domain.grade.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodModifyRequest;
import com.ssafy.bid.domain.grade.service.CoreGradeService;
import com.ssafy.bid.domain.grade.service.GradeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class GradeApi {

	private final GradeService gradeService;
	private final CoreGradeService coreGradeService;

	@GetMapping("/{gradeNo}/statistics")
	public ResponseEntity<GradeStatisticsFindResponse> findGrade(@PathVariable int gradeNo) {
		GradeStatisticsFindResponse response = coreGradeService.findGradeStatistics(gradeNo);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{gradeNo}/salaries")
	public void modifySalary(@PathVariable int gradeNo, @RequestBody SalaryModifyRequest salaryModifyRequest) {
		gradeService.modifySalary(gradeNo, salaryModifyRequest);
	}

	@PatchMapping("/{gradeNo}/saving-periods")
	public void modifySavingPeriod(@PathVariable int gradeNo,
		@RequestBody SavingPeriodModifyRequest savingPeriodModifyRequest) {
		gradeService.modifySavingTime(gradeNo, savingPeriodModifyRequest);
	}
}
