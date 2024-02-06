package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TelAuthenticationSendResponse {
	private String code;

	public TelAuthenticationSendResponse(
		String code
	) {
		this.code = code;
	}
}
