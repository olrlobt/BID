package com.ssafy.bid.domain.grade.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BiddingsStatisticsResponse {
	private String date;
	private long count;

	public BiddingsStatisticsResponse(
		String date,
		long count
	) {
		this.date = date;
		this.count = count;
	}
}
