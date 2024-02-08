package com.ssafy.bid.domain.user.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.user.Admin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminSaveRequest {
	private String id;
	private String password;
	private String confirmPassword;
	private String name;
	private String tel;
	private int schoolNo;

	public Admin toEntity(PasswordEncoder passwordEncoder) {
		return Admin.builder()
			.id(id)
			.password(passwordEncoder.encode(password))
			.name(name)
			.schoolNo(schoolNo)
			.tel(tel)
			.mainGradeNo(null)
			.build();
	}
}
