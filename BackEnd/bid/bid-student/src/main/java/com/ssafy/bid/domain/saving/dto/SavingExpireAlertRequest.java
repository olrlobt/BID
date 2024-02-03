package com.ssafy.bid.domain.saving.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingExpireAlertRequest {
	private int price;
	private LocalDate endDate;
	private int userNo;

	@Builder
	public SavingExpireAlertRequest(
		int price,
		LocalDate endDate,
		int userNo
	) {
		this.price = price;
		this.endDate = endDate;
		this.userNo = userNo;
	}
}
