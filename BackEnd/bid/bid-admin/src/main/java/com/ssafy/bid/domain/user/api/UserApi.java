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

import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.AdminSaveRequest;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.StudentSaveRequest;
import com.ssafy.bid.domain.user.dto.StudentsGetResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
import com.ssafy.bid.domain.user.dto.UserDeleteRequest;
import com.ssafy.bid.domain.user.dto.UserIdFindRequest;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.service.CoreUserService;
import com.ssafy.bid.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserApi {

	private final UserService userService;
	private final CoreUserService coreUserService;

	@PostMapping("/send-code")
	public ResponseEntity<TelAuthenticationSendResponse> sendCode(
		@RequestBody TelAuthenticationSendRequest telAuthenticationSendRequest
	) {
		TelAuthenticationSendResponse response = userService.sendTelAuthentication(telAuthenticationSendRequest);
		return ResponseEntity.status(OK).body(response);
	}

	@GetMapping("/check-id")
	public ResponseEntity<Boolean> checkIdDuplicate(@RequestParam String id) {
		boolean isDuplicate = userService.isDuplicated(id);
		return ResponseEntity.ok().body(isDuplicate);
	}

	@GetMapping("/schools")
	public ResponseEntity<List<SchoolsFindResponse>> getSchools(@RequestParam String name) {
		List<SchoolsFindResponse> response = userService.getSchools(name);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveAdmin(@RequestBody AdminSaveRequest request) {
		userService.saveAdmin(request);
		return ResponseEntity.status(CREATED).build();
	}

	@PostMapping("/students")
	public ResponseEntity<Void> saveStudent(@RequestBody StudentSaveRequest request) {
		userService.saveStudent(request);
		return ResponseEntity.status(CREATED).build();
	}

	@PatchMapping("/password/{userNo}")
	public ResponseEntity<?> resetStudentPassword(@PathVariable int userNo) {
		userService.resetStudentPassword(userNo);
		return ResponseEntity.status(OK).build();
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

	@GetMapping("/{gradeNo}/users")
	public ResponseEntity<List<StudentsGetResponse>> findStudents(@PathVariable int gradeNo) {
		List<StudentsGetResponse> responses = userService.getStudents(gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@GetMapping("/{grageNo}/users/{userNo}")
	public ResponseEntity<StudentFindResponse> findStudent(
		@PathVariable int userNo,
		@ModelAttribute StudentFindRequest studentFindRequest
	) {
		StudentFindResponse response = coreUserService.findStudent(userNo, studentFindRequest);
		return ResponseEntity.status(OK).body(response);
	}

	@GetMapping("/{gradeNo}/users/{userNo}/accounts")
	public ResponseEntity<List<AccountFindResponse>> findAccount(
		@PathVariable int userNo,
		@ModelAttribute AccountFindRequest accountFindRequest
	) {
		List<AccountFindResponse> responses = coreUserService.findAccount(userNo, accountFindRequest);
		return ResponseEntity.status(OK).body(responses);
	}

	@PostMapping("/find-id")
	public ResponseEntity<String> findUserId(@RequestBody UserIdFindRequest request) {
		String id = userService.getUserId(request);
		return ResponseEntity.ok(id);
	}

	@PatchMapping("/{gradeNo}/users/{userNo}")
	public ResponseEntity<?> updateUser(@PathVariable Integer userNo, @RequestBody UserUpdateRequest request) {
		userService.updateUser(userNo, request);
		return ResponseEntity.status(OK).build();
	}

	@DeleteMapping("/{gradeNo}/users/{userNo}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userNo, @RequestBody UserDeleteRequest request) {
		userService.deleteUser(userNo, request);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@GetMapping("/{gradeNo}/balls")
	public ResponseEntity<List<BallsFindResponse>> findAllBalls(@PathVariable int gradeNo) {
		List<BallsFindResponse> responses = userService.getAllBalls(gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@PatchMapping("/{gradeNo}/balls")
	public ResponseEntity<?> resetAllBalls(@PathVariable int gradeNo) {
		userService.resetAllBalls(gradeNo);
		return ResponseEntity.status(OK).build();
	}
}
