package com.ssafy.bid.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BallsResponse {
	private int no;
	private String name;
	private int ballCount;

	public BallsResponse(
		int no,
		String name,
		int ballCount
	) {
		this.no = no;
		this.name = name;
		this.ballCount = ballCount;
	}
}
