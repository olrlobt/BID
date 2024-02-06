package com.ssafy.bid.domain.user.security;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.User;

import lombok.Getter;

@Getter
public class CustomUserInfo {
	private final String id;
	private final String password;
	private final String name;
	private final Integer schoolNo;
	private final String tel;

	public CustomUserInfo(User user) {
		this.id = user.getId();
		this.password = user.getPassword();
		this.name = user.getName();
		this.schoolNo = user.getSchoolNo();
		this.tel = (user instanceof Admin) ? ((Admin)user).getTel() : null;
	}
}
