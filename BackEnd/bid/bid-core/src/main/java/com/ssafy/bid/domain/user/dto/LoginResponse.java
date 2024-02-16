package com.ssafy.bid.domain.user.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class LoginResponse {
	private TokenResponse tokenResponse;
	private List<StudentInfo> studentList;
	private StudentInfo myInfo;
	private AdminInfo adminInfo;

	public LoginResponse(
		TokenResponse tokenResponse,
		List<StudentInfo> studentList,
		StudentInfo myInfo,
		AdminInfo adminInfo
	) {
		this.tokenResponse = tokenResponse;
		this.studentList = studentList;
		this.myInfo = myInfo;
		this.adminInfo = adminInfo;
	}
}
