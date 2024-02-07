package com.ssafy.bid.domain.grade.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BiddingStatisticsGetResponse {
	private LocalDate date;
	private int count;

	@Builder
	public BiddingStatisticsGetResponse(
		LocalDate date,
		int count
	) {
		this.date = date;
		this.count = count;
	}
}
