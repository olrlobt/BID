package com.ssafy.bid.domain.user.dto;

import lombok.Getter;

@Getter
public class PasswordUpdateRequest {
	private String password;
	private String newPassword;
	private String newPasswordCheck;
}
