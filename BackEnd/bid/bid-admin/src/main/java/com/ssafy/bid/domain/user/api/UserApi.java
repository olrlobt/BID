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
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.FindUserIDRequest;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.RegisterRequest;
import com.ssafy.bid.domain.user.dto.SchoolResponse;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.StudentRegistrationRequest;
import com.ssafy.bid.domain.user.dto.StudentsFindResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
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

	/**
	 * 인증 코드 전송
	 * 아이디 중복 여부 체크
	 * 학교 목록 조회
	 * 관리자 회원가입
	 * 학생 회원가입
	 */
	@PostMapping("/send-code")
	public ResponseEntity<TelAuthenticationSendResponse> sendCode(
		@RequestBody TelAuthenticationSendRequest telAuthenticationSendRequest) {
		TelAuthenticationSendResponse response = userService.sendTelAuthentication(telAuthenticationSendRequest);
		return ResponseEntity.status(OK).body(response);
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

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
		userService.registerUser(request);
		return ResponseEntity.status(CREATED).build();
	}

	@PostMapping("/students")
	public ResponseEntity<Void> registerStudent(@RequestBody StudentRegistrationRequest request) {
		userService.registerStudent(request);
		return ResponseEntity.status(CREATED).build();
	}

	/**
	 * 로그인
	 * 로그아웃
	 */
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

	/**
	 * 학생 목록 조회
	 * 학생 상세 조회
	 * 학생 입출금 내역 상세 조회
	 */
	@GetMapping("/{gradeNo}/users")
	public ResponseEntity<List<StudentsFindResponse>> findStudents(@PathVariable int gradeNo) {
		List<StudentsFindResponse> responses = userService.findAllStudents(gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@GetMapping("{grageNo}/users/{userNo}")
	public ResponseEntity<StudentFindResponse> findStudent(
		@PathVariable int userNo,
		@ModelAttribute StudentFindRequest studentFindRequest
	) {
		StudentFindResponse response = coreUserService.findStudent(userNo, studentFindRequest);
		return ResponseEntity.status(OK).body(response);
	}

	@GetMapping("{gradeNo}/users/{userNo}/accounts")
	public ResponseEntity<List<AccountFindResponse>> findAccount(
		@PathVariable int userNo,
		@ModelAttribute AccountFindRequest accountFindRequest
	) {
		List<AccountFindResponse> responses = coreUserService.findAccount(userNo, accountFindRequest);
		return ResponseEntity.status(OK).body(responses);
	}

	/**
	 * 아이디 찾기
	 * 회원 정보 수정
	 * 회원 탈퇴
	 */
	@PostMapping("/find-id")
	public ResponseEntity<String> findUserId(@RequestBody FindUserIDRequest request) {
		String id = userService.findUserId(request.getName(), request.getTel());
		return ResponseEntity.ok(id);
	}

	@PatchMapping("/users/{userNo}")
	public ResponseEntity<?> updateUser(@PathVariable Integer userNo, @RequestBody UserUpdateRequest request) {
		userService.updateUser(userNo, request);
		return ResponseEntity.status(OK).build();
	}

	@DeleteMapping("/users/{userNo}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userNo, @RequestBody UserWithdrawalRequest request) {
		userService.deleteUser(userNo, request);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	/**
	 * 보유 공 개수 목록 조회
	 * 보유 공 개수 초기화
	 */
	@GetMapping("/{gradeNo}/balls")
	public ResponseEntity<List<BallsFindResponse>> findAllBalls(@PathVariable int gradeNo) {
		List<BallsFindResponse> responses = userService.findAllBalls(gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@PatchMapping("/{gradeNo}/balls")
	public ResponseEntity<?> modifyBalls(@PathVariable int gradeNo) {
		userService.modifyBalls(gradeNo);
		return ResponseEntity.status(OK).build();
	}
}
