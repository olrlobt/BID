package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminPasswordUpdateRequest {
	private String id;
	private String tel;
	private String newPassword;
	private String newPasswordCheck;
}
