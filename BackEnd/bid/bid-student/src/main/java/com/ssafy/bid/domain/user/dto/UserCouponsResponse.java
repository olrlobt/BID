package com.ssafy.bid.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCouponsResponse {
	private String name;
	private long count;

	public UserCouponsResponse(
		String name,
		long count
	) {
		this.name = name;
		this.count = count;
	}
}
