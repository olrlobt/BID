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

	//TODO: SuperBuilder 등 적용 방안 적용
	public Admin toEntity(PasswordEncoder passwordEncoder) {
		return new Admin(id, passwordEncoder.encode(password), name, schoolNo, tel);
	}
}
