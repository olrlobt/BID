package com.ssafy.bid.domain.user.api;

import com.ssafy.bid.domain.user.dto.*;
import com.ssafy.bid.domain.user.service.AuthService;
import java.util.List;

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
>>>>>>> BackEnd/bid/bid-admin/src/main/java/com/ssafy/bid/domain/user/api/UserApi.java
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

	@GetMapping("/{gradeNo}/balls")
	public List<BallsResponse> findBalls(@PathVariable int gradeNo) {
		return userService.findBalls(gradeNo);
	}

	@PatchMapping("/{gradeNo}/balls")
	public void modifyBalls(@PathVariable int gradeNo) {
		userService.modifyBalls(gradeNo);
	}
}
