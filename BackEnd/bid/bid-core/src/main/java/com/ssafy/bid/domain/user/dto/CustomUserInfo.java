package com.ssafy.bid.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomUserInfo {
	private int no;
	private String id;
	private String password;
	private String name;
	private int schoolNo;
	private int gradeNo;
	private String tel;

	@Builder
	public CustomUserInfo(
		int no,
		String id,
		String password,
		String name,
		int schoolNo,
		int gradeNo,
		String tel
	) {
		this.no = no;
		this.id = id;
		this.password = password;
		this.name = name;
		this.schoolNo = schoolNo;
		this.gradeNo = gradeNo;
		this.tel = tel;
	}
}
