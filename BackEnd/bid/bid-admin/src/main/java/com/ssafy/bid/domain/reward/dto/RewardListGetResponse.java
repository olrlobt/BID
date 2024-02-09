package com.ssafy.bid.domain.reward.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RewardListGetResponse {
	private int no;
	private String name;
	private int price;

	public RewardListGetResponse(
		int no,
		String name,
		int price
	) {
		this.no = no;
		this.name = name;
		this.price = price;
	}
}
