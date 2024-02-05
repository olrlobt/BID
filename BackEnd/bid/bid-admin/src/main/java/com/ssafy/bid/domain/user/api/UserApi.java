package com.ssafy.bid.domain.user.api;

import com.ssafy.bid.domain.user.dto.*;
import com.ssafy.bid.domain.user.service.AuthService;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserApi {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{gradeNo}/users")
    public List<StudentsResponse> findStudents(@PathVariable int gradeNo) {
        return userService.findStudents(gradeNo);
    }

    @GetMapping("/users/{userNo}")
    public StudentResponse findStudent(@PathVariable int userNo, @ModelAttribute StudentRequest studentRequest) {
        return userService.findStudent(userNo, studentRequest);
    }

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
		return ResponseEntity.ok().body(isDuplicate);
	}

	@GetMapping("/schools")
	public ResponseEntity<List<SchoolResponse>> searchSchools(@RequestParam String name) {
		List<School> schools = userService.searchSchools(name);
		List<SchoolResponse> schoolResponses = schools.stream()
				.map(school -> new SchoolResponse(school.getNo(), school.getName(), school.getRegion(), school.getCode()))
				.collect(Collectors.toList());
		return ResponseEntity.ok(schoolResponses);
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
		try {
			String userId = userService.findUserId(request.getName(), request.getTel());
			return ResponseEntity.ok(userId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/users/{userNo}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userNo, @RequestBody UserWithdrawalRequest request) {
		try {
			userService.deleteUser(userNo, request.getPassword());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping("/users/{userNo}")
	public ResponseEntity<?> updateUser(@PathVariable Integer userNo, @RequestBody UserUpdateRequest request) {
		try {
			userService.updateUser(userNo, request);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/students")
	public ResponseEntity<Void> registerStudent(@RequestBody StudentRegistrationRequest request) {
			userService.registerStudent(request);
			return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
