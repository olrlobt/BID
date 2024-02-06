package com.ssafy.bid.domain.user.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.BallsResponse;
import com.ssafy.bid.domain.user.dto.FindUserIDRequest;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.RegisterRequest;
import com.ssafy.bid.domain.user.dto.SchoolResponse;
import com.ssafy.bid.domain.user.dto.StudentRegistrationRequest;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.dto.UserWithdrawalRequest;
import com.ssafy.bid.domain.user.service.CoreUserService;
import com.ssafy.bid.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserApi {

	private final UserService userService;
	private final CoreUserService coreUserService;

	@GetMapping("/{gradeNo}/users")
	public List<StudentsResponse> findStudents(@PathVariable int gradeNo) {
		return userService.findStudents(gradeNo);
	}

	@GetMapping("/users/{userNo}")
	public StudentResponse findStudent(@PathVariable int userNo, @ModelAttribute StudentRequest studentRequest) {
		return userService.findStudent(userNo, studentRequest);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {
		String token = coreUserService.login(request);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authToken) {
		coreUserService.logout(authToken);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/users/{userNo}/accounts")
	public List<AccountResponse> findAccount(@PathVariable int userNo, @ModelAttribute AccountRequest accountRequest) {
		return userService.findAccount(userNo, accountRequest);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
		userService.registerUser(request);
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping("/check-id")
	public ResponseEntity<Boolean> checkIdDuplicate(@RequestParam String id) {
		boolean isDuplicate = userService.isIdDuplicate(id);
		return ResponseEntity.ok().body(isDuplicate);
	}

	@GetMapping("/schools")
	public ResponseEntity<List<SchoolResponse>> searchSchools(@RequestParam String name) {
		List<SchoolResponse> response = userService.searchSchools(name);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{gradeNo}/balls")
	public List<BallsResponse> findBalls(@PathVariable int gradeNo) {
		return userService.findBalls(gradeNo);
	}

	@PatchMapping("/{gradeNo}/balls")
	public void modifyBalls(@PathVariable int gradeNo) {
		userService.modifyBalls(gradeNo);
	}

	@PostMapping("/find-id")
	public ResponseEntity<String> findUserId(@RequestBody FindUserIDRequest request) {
		String id = userService.findUserId(request.getName(), request.getTel());
		return ResponseEntity.ok(id);
	}

	@DeleteMapping("/users/{userNo}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userNo, @RequestBody UserWithdrawalRequest request) {
		userService.deleteUser(userNo, request);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@PatchMapping("/users/{userNo}")
	public ResponseEntity<?> updateUser(@PathVariable Integer userNo, @RequestBody UserUpdateRequest request) {
		userService.updateUser(userNo, request);
		return ResponseEntity.status(OK).build();
	}

	@PostMapping("/students")
	public ResponseEntity<Void> registerStudent(@RequestBody StudentRegistrationRequest request) {
		userService.registerStudent(request);
		return ResponseEntity.status(CREATED).build();
	}
}
