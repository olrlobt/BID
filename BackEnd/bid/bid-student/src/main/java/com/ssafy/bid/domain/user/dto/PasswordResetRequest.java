package com.ssafy.bid.domain.user.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
}
