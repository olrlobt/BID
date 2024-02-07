package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteRequest {
	private String password;

	public UserDeleteRequest(String password) {
		this.password = password;
	}
}
