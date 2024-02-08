package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TelAuthenticationSendRequest {
	private String tel;

	public TelAuthenticationSendRequest(
		String tel
	) {
		this.tel = tel;
	}
}
