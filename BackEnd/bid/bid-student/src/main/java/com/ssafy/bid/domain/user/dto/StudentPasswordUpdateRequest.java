package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentPasswordUpdateRequest {
	private String currentPassword;
	private String newPassword;
	private String newPasswordCheck;
}
