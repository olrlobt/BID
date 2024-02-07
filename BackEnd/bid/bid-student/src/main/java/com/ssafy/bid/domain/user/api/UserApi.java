package com.ssafy.bid.domain.user.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.AttendanceResponse;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.PasswordResetRequest;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.service.CoreUserService;
import com.ssafy.bid.domain.user.service.CustomUserDetails;
import com.ssafy.bid.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserApi {
	private final UserService userService;
	private final CoreUserService coreUserService;

	@PostMapping("/password")
	public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		userService.resetPassword(userId, request);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/users/attendance/check")
	public ResponseEntity<?> checkAttendance(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		int userNo = userDetails.getUserInfo().getNo();
		userService.checkAttendance(userNo);
		return ResponseEntity.status(OK).build();
	}

	@GetMapping("/users/{userNo}/attendance")
	public ResponseEntity<AttendanceResponse> getStudentAttendance(@PathVariable Integer userNo) {
		AttendanceResponse response = userService.getStudentAttendance(userNo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/users/{userNo}")
	public ResponseEntity<StudentFindResponse> findStudent(
		@PathVariable int userNo,
		@ModelAttribute StudentFindRequest studentFindRequest
	) {
		StudentFindResponse response = coreUserService.findStudent(userNo, studentFindRequest);
		return ResponseEntity.status(OK).body(response);
	}

	@GetMapping("/users/accounts")
	public ResponseEntity<List<AccountFindResponse>> findAccount(
		// @AuthenticationPrincipal CustomUserDetails userDetails,
		@ModelAttribute AccountFindRequest accountFindRequest
	) {
		// int userNo = userDetails.getUserInfo().getNo();
		List<AccountFindResponse> responses = coreUserService.findAccount(2, accountFindRequest);
		return ResponseEntity.status(OK).body(responses);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {
		String token = coreUserService.login(request);
		return ResponseEntity.status(OK).body(token);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authToken) {
		coreUserService.logout(authToken);
		return ResponseEntity.ok().build();
	}
}
