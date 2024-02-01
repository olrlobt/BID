package com.ssafy.bid.domain.user.api;

import com.ssafy.bid.domain.user.dto.*;
import com.ssafy.bid.domain.user.service.AuthService;
import com.ssafy.bid.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
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

    @GetMapping("/users/{userNo}/accounts")
    public List<AccountResponse> findAccount(@PathVariable int userNo, @ModelAttribute AccountRequest accountRequest) {
        return userService.findAccount(userNo, accountRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = this.authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
