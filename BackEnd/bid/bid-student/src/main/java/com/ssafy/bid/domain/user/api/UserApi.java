package com.ssafy.bid.domain.user.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.LoginResponse;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.TokenResponse;
import com.ssafy.bid.domain.user.service.CoreUserService;
import com.ssafy.bid.domain.user.service.CustomUserDetails;
import com.ssafy.bid.domain.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserApi {
	private final UserService userService;
	private final CoreUserService coreUserService;

	@PatchMapping("/users/attendance/check")
	public ResponseEntity<?> checkAttendance(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		int userNo = userDetails.getUserInfo().getNo();
		userService.checkAttendance(userNo);
		return ResponseEntity.status(OK).build();
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
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@ModelAttribute AccountFindRequest accountFindRequest
	) {
		int userNo = userDetails.getUserInfo().getNo();
		List<AccountFindResponse> responses = coreUserService.findAccount(userNo, accountFindRequest);
		return ResponseEntity.status(OK).body(responses);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse) {
		LoginResponse loginResponse = coreUserService.login(request, false);
		TokenResponse tokenResponse = loginResponse.getTokenResponse();
		Cookie cookie = createCookie(tokenResponse.getAccessToken());
		httpResponse.addCookie(cookie);
		return ResponseEntity.ok(loginResponse);
	}

	private Cookie createCookie(String accessToken) {
		Cookie cookie = new Cookie("accessToken", accessToken);
		cookie.setHttpOnly(true);
		// cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 30);
		return cookie;
	}

	@GetMapping("/signout")
	public ResponseEntity<?> logout(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		HttpServletRequest request
	) {
		int userNo = userDetails.getUserInfo().getNo();
		coreUserService.logout(userNo, request);
		return ResponseEntity.ok().build();
	}
}
