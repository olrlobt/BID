package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIdFindRequest {
	private String name;
	private String tel;

	public UserIdFindRequest(String name, String tel) {
		this.name = name;
		this.tel = tel;
	}
}
