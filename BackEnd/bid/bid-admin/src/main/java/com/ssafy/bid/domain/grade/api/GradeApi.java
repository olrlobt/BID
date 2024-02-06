package com.ssafy.bid.domain.grade.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.grade.dto.GradeCreationRequest;
import com.ssafy.bid.domain.grade.dto.GradeDTO;
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

	@PostMapping("/grade/register")
	public ResponseEntity<Void> createGrade(@RequestBody GradeCreationRequest request) {
		gradeService.createGrade(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/grade")
	public ResponseEntity<List<GradeDTO>> listGrades() {
		List<GradeDTO> grades = gradeService.listGrades();
		return ResponseEntity.ok(grades);
	}

	@DeleteMapping("/grade/{gradeNo}")
	public ResponseEntity<Void> deleteGrade(@PathVariable Integer gradeNo) {
		gradeService.deleteGrade(gradeNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

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
