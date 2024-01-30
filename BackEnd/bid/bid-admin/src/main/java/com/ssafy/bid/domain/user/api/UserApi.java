package com.ssafy.bid.domain.user.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApi {

	private final UserService userService;

	@GetMapping("/{gradeNo}/users")
	public Map<String, List<StudentsResponse>> findStudents(@PathVariable int gradeNo) {
		List<StudentsResponse> responses = userService.findStudents(gradeNo);
		return Map.of("body", responses);
	}
}
