package com.ssafy.bid.domain.user.dto;

import lombok.Getter;

@Getter
public class AdminInfo {
	private int userNo;
	private int schoolNo;
	private String schoolCode;
	private String schoolName;
	private String adminName;

	public AdminInfo(
		int userNo,
		int schoolNo,
		String schoolCode,
		String schoolName,
		String adminName
	) {
		this.userNo = userNo;
		this.schoolNo = schoolNo;
		this.schoolCode = schoolCode;
		this.schoolName = schoolName;
		this.adminName = adminName;
	}
}
