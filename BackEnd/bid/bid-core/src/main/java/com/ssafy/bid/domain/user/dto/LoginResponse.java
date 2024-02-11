package com.ssafy.bid.domain.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LoginResponse {
	private TokenResponse tokenResponse;
	private List<StudentInfo> studentList;

	public LoginResponse(TokenResponse tokenResponse, List<StudentInfo> studentList) {
		this.tokenResponse = tokenResponse;
		this.studentList = studentList;
	}
}
