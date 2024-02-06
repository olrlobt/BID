package com.ssafy.bid.domain.user.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.user.Admin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
	private String id;
	private String password;
	private String name;
	private String tel;
	private int schoolNo;

	public Admin toEntity(PasswordEncoder passwordEncoder) {
		return new Admin(id, passwordEncoder.encode(password), name, schoolNo, tel);
	}
}
