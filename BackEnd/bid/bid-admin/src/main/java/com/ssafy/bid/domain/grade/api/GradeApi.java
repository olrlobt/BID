package com.ssafy.bid.domain.grade.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.ssafy.bid.domain.grade.dto.GradeUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SalaryUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodUpdateRequest;
import com.ssafy.bid.domain.grade.service.CoreGradeService;
import com.ssafy.bid.domain.grade.service.GradeService;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class GradeApi {
	private final GradeService gradeService;
	private final CoreGradeService coreGradeService;

	@PostMapping("/grades")
	public ResponseEntity<?> saveGrade(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody GradeSaveRequest request
	) {
		int userNo = userDetails.getUserInfo().getNo();
		gradeService.saveGrade(userNo, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/grades")
	public ResponseEntity<List<GradeListGetResponse>> getGrades(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		CustomUserInfo userInfo = userDetails.getUserInfo();
		List<GradeListGetResponse> responses = gradeService.getGrades(userInfo);
		return ResponseEntity.status(OK).body(responses);
	}

	@PatchMapping("/grades")
	public ResponseEntity<?> updateMainGrade(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody GradeUpdateRequest gradeUpdateRequest
	) {
		int userNo = userDetails.getUserInfo().getNo();
		gradeService.updateMainGrade(userNo, gradeUpdateRequest);
		return ResponseEntity.status(OK).build();
	}

	@DeleteMapping("/grades/{gradeNo}")
	public ResponseEntity<?> deleteGrade(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo
	) {
		UserType userType = userDetails.getUserInfo().getUserType();
		gradeService.deleteGrade(userType, gradeNo);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@GetMapping("/{gradeNo}/statistics")
	public ResponseEntity<GradeStatisticsGetResponse> findGrade(@PathVariable int gradeNo) {
		GradeStatisticsGetResponse response = coreGradeService.getGradeStatistics(gradeNo);
		return ResponseEntity.status(OK).body(response);
	}

	@PatchMapping("/{gradeNo}/salaries")
	public ResponseEntity<?> updateSalary(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo,
		@RequestBody SalaryUpdateRequest salaryUpdateRequest
	) {
		UserType userType = userDetails.getUserInfo().getUserType();
		gradeService.updateSalary(userType, gradeNo, salaryUpdateRequest);
		return ResponseEntity.status(OK).build();
	}

	@PatchMapping("/{gradeNo}/saving-periods")
	public ResponseEntity<?> updateSavingPeriod(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo,
		@RequestBody SavingPeriodUpdateRequest savingPeriodUpdateRequest
	) {
		UserType userType = userDetails.getUserInfo().getUserType();
		gradeService.updateSavingPeriod(userType, gradeNo, savingPeriodUpdateRequest);
		return ResponseEntity.status(OK).build();
	}
}
