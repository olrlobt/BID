package com.ssafy.bid.domain.grade.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodModifyRequest;
import com.ssafy.bid.domain.grade.service.GradeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class GradeApi {

	private final GradeService gradeService;

	@GetMapping("/{gradeNo}/statistics")
	public GradeFindResponse findGrade(@PathVariable int gradeNo) {
		return gradeService.findGrade(gradeNo);
	}

	@PatchMapping("/{gradeNo}/salaries")
	public void modifySalary(@PathVariable int gradeNo, SalaryModifyRequest salaryModifyRequest) {
		gradeService.modifySalary(gradeNo, salaryModifyRequest);
	}

	@PatchMapping("/{gradeNo}/saving-periods")
	public void modifySavingPeriod(@PathVariable int gradeNo, SavingPeriodModifyRequest savingPeriodModifyRequest) {
		gradeService.modifySavingTime(gradeNo, savingPeriodModifyRequest);
	}
}
