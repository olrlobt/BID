package com.ssafy.bid.domain.reward.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RewardsFindResponse {
	private int no;
	private String name;
	private int price;

	public RewardsFindResponse(
		int no,
		String name,
		int price
	) {
		this.no = no;
		this.name = name;
		this.price = price;
	}
}
