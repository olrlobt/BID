package com.ssafy.bid.domain.user.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

=======
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
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

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
		try {
			userService.registerUser(request);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
		}
	}

	@GetMapping("/check-id")
	public ResponseEntity<?> checkIdDuplicate(@RequestParam String id) {
		boolean isDuplicate = userService.isIdDuplicate(id);
		return ResponseEntity.ok().body(Map.of("dulplicated", isDuplicate));
	}

	@GetMapping("/schools")
	public ResponseEntity<List<SchoolResponse>> searchSchools(@RequestParam String name) {
		List<School> schools = userService.searchSchools(name);
		List<SchoolResponse> schoolResponses = schools.stream()
				.map(school -> new SchoolResponse(school.getNo(), school.getName(), school.getRegion(), school.getCode()))
				.collect(Collectors.toList());
		return ResponseEntity.ok(schoolResponses);
		
	@GetMapping("/{gradeNo}/balls")
	public List<BallsResponse> findBalls(@PathVariable int gradeNo) {
		return userService.findBalls(gradeNo);
	}

	@PatchMapping("/{gradeNo}/balls")
	public void modifyBalls(@PathVariable int gradeNo) {
		userService.modifyBalls(gradeNo);
	}
}
