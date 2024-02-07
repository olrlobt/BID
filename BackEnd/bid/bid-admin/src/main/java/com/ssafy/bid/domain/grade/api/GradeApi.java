package com.ssafy.bid.domain.grade.api;

import static org.springframework.http.HttpStatus.*;

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

import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;
import com.ssafy.bid.domain.grade.dto.GradeSaveRequest;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsGetResponse;
import com.ssafy.bid.domain.grade.dto.SalaryUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodUpdateRequest;
import com.ssafy.bid.domain.grade.service.CoreGradeService;
import com.ssafy.bid.domain.grade.service.GradeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class GradeApi {
	private final GradeService gradeService;
	private final CoreGradeService coreGradeService;

	@PostMapping("/grades")
	public ResponseEntity<?> saveGrade(@RequestBody GradeSaveRequest request) {
		gradeService.saveGrade(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/grades")
	public ResponseEntity<List<GradeListGetResponse>> getGrades() {
		List<GradeListGetResponse> responses = gradeService.getGrades();
		return ResponseEntity.status(OK).body(responses);
	}

	@DeleteMapping("/grades/{gradeNo}")
	public ResponseEntity<?> deleteGrade(@PathVariable int gradeNo) {
		gradeService.deleteGrade(gradeNo);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@GetMapping("/{gradeNo}/statistics")
	public ResponseEntity<GradeStatisticsGetResponse> findGrade(@PathVariable int gradeNo) {
		GradeStatisticsGetResponse response = coreGradeService.findGradeStatistics(gradeNo);
		return ResponseEntity.status(OK).body(response);
	}

	@PatchMapping("/{gradeNo}/salaries")
	public ResponseEntity<?> updateSalary(@PathVariable int gradeNo,
		@RequestBody SalaryUpdateRequest salaryUpdateRequest) {
		gradeService.updateSalary(gradeNo, salaryUpdateRequest);
		return ResponseEntity.status(OK).build();
	}

	@PatchMapping("/{gradeNo}/saving-periods")
	public ResponseEntity<?> updateSavingPeriod(@PathVariable int gradeNo,
		@RequestBody SavingPeriodUpdateRequest savingPeriodUpdateRequest) {
		gradeService.updateSavingPeriod(gradeNo, savingPeriodUpdateRequest);
		return ResponseEntity.status(OK).build();
	}
}
