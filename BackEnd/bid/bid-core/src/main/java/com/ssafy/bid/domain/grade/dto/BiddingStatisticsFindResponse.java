package com.ssafy.bid.domain.grade.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BiddingStatisticsFindResponse {
	private LocalDate date;
	private int count;

	@Builder
	public BiddingStatisticsFindResponse(
		LocalDate date,
		int count
	) {
		this.date = date;
		this.count = count;
	}
}
