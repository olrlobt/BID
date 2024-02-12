package com.ssafy.bid.domain.user.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class LoginResponse {
	private TokenResponse tokenResponse;
	private List<StudentInfo> studentList;

	public LoginResponse(TokenResponse tokenResponse, List<StudentInfo> studentList) {
		this.tokenResponse = tokenResponse;
		this.studentList = studentList;
	}
}
