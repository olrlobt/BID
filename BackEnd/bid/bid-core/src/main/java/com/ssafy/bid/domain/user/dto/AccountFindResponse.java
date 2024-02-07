package com.ssafy.bid.domain.user.dto;

import java.time.LocalDateTime;

import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountFindResponse {
	private AccountType accountType;
	private int price;
	private String content;
	private DealType dealType;
	private LocalDateTime createdAt;

	public AccountFindResponse(
		AccountType accountType,
		int price,
		String content,
		DealType dealType,
		LocalDateTime createdAt
	) {
		this.accountType = accountType;
		this.price = price;
		this.content = content;
		this.dealType = dealType;
		this.createdAt = createdAt;
	}
}
