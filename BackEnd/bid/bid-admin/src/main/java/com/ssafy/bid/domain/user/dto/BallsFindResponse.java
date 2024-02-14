package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BallsFindResponse {
	private int no;
	private String name;
	private int ballCount;

	public BallsFindResponse(
		int no,
		String name,
		int ballCount
	) {
		this.no = no;
		this.name = name;
		this.ballCount = ballCount;
	}
}
