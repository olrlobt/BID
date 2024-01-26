package com.ssafy.bid.domain.user;

import lombok.Getter;

@Getter
public enum AccountType {

	EXPENDITURE("지출"),
	INCOME("수입");

	private final String accountType;

	private AccountType(String accountType) {
		this.accountType = accountType;
	}
}
