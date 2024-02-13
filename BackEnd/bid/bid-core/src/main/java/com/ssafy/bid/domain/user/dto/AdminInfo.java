package com.ssafy.bid.domain.user.dto;

import lombok.Getter;

@Getter
public class AdminInfo {
	private int userNo;
	private int schoolNo;
	private String schoolCode;

	public AdminInfo(
		int userNo,
		int schoolNo,
		String schoolCode
	) {
		this.userNo = userNo;
		this.schoolNo = schoolNo;
		this.schoolCode = schoolCode;
	}
}
