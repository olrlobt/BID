package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserWithdrawalRequest {
	private String password;

	public UserWithdrawalRequest(String password) {
		this.password = password;
	}
}
