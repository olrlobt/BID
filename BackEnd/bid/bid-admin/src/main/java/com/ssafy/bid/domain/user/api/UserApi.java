package com.ssafy.bid.domain.user.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.BallsResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApi {

	private final UserService userService;

	@GetMapping("/{gradeNo}/users")
	public List<StudentsResponse> findStudents(@PathVariable int gradeNo) {
		return userService.findStudents(gradeNo);
	}

	@GetMapping("/users/{userNo}")
	public StudentResponse findStudent(@PathVariable int userNo, @ModelAttribute StudentRequest studentRequest) {
		return userService.findStudent(userNo, studentRequest);
	}

	@GetMapping("/users/{userNo}/accounts")
	public List<AccountResponse> findAccount(@PathVariable int userNo, @ModelAttribute AccountRequest accountRequest) {
		return userService.findAccount(userNo, accountRequest);
	}

	@GetMapping("/{gradeNo}/balls")
	public List<BallsResponse> findBalls(@PathVariable int gradeNo) {
		return userService.findBalls(gradeNo);
	}

	@PatchMapping("/{gradeNo}/balls")
	public void modifyBalls(@PathVariable int gradeNo) {
		userService.modifyBalls(gradeNo);
	}
}
