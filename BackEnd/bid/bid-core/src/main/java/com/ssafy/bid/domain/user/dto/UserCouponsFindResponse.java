package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCouponsFindResponse {
	private String name;
	private long count;

	public UserCouponsFindResponse(
		String name,
		long count
	) {
		this.name = name;
		this.count = count;
	}
}
