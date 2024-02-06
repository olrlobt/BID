package com.ssafy.bid.domain.user.api;

import com.ssafy.bid.domain.user.dto.AttendanceResponse;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.PasswordResetRequest;
import com.ssafy.bid.domain.user.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserApi {
	private final AuthService authService;
	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
		String token = this.authService.login(request);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authToken) {
		authService.logout(authToken);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/password")
	public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		userService.resetPassword(userId, request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/users/{userNo}/attendance")
	public ResponseEntity<AttendanceResponse> getStudentAttendance(@PathVariable Integer userNo) {
		AttendanceResponse response = userService.getStudentAttendance(userNo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/users/{userNo}")
	public StudentResponse findStudent(@PathVariable int userNo, @ModelAttribute StudentRequest studentRequest) {
		return userService.findStudent(userNo, studentRequest);
	}

	@GetMapping("/users/accounts")
	public List<AccountResponse> findAccount(@ModelAttribute AccountRequest accountRequest) {
		//TODO: SecurityUser 받아오기
		return userService.findAccount(2, accountRequest);
	}
}
