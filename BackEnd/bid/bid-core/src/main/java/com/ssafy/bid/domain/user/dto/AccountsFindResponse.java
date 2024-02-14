package com.ssafy.bid.domain.user.dto;

import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountsFindResponse {
	private int day;
	private int totalPrice;
	private AccountType accountType;
	private DealType dealType;

	public AccountsFindResponse(
		int day,
		int totalPrice,
		AccountType accountType,
		DealType dealType
	) {
		this.day = day;
		this.totalPrice = totalPrice;
		this.accountType = accountType;
		this.dealType = dealType;
	}
}
