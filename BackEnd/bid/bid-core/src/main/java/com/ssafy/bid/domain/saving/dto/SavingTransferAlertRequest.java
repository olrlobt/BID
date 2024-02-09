package com.ssafy.bid.domain.saving.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingTransferAlertRequest {
	private int userNo;
	private int price;
	private LocalTime transferPeriod;

	public SavingTransferAlertRequest(
		int userNo,
		int price,
		LocalTime transferPeriod
	) {
		this.userNo = userNo;
		this.price = price;
		this.transferPeriod = transferPeriod;
	}
}
