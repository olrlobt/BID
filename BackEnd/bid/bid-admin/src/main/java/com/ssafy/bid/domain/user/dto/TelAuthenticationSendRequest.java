package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TelAuthenticationSendRequest {
	private String id;
	private String tel;

	public TelAuthenticationSendRequest(
		String id,
		String tel
	) {
		this.id = id;
		this.tel = tel;
	}
}
