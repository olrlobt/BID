package com.ssafy.bid.domain.grade.dto;

import com.ssafy.bid.domain.user.DealType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountsStatisticsResponse {
	private DealType dealType;
	private int sum;

	public AccountsStatisticsResponse(
		DealType dealType,
		int sum
	) {
		this.dealType = dealType;
		this.sum = sum;
	}
}
