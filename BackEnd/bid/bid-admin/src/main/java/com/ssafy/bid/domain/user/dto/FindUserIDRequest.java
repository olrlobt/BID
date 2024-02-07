package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindUserIDRequest {
	private String name;
	private String tel;

	public FindUserIDRequest(String name, String tel) {
		this.name = name;
		this.tel = tel;
	}
}
