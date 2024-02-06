package com.ssafy.bid.domain.grade.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WinningBiddingStatisticsFindResponse {
	private int daysBefore;
	private int count;
	private int gradeNo;

	public WinningBiddingStatisticsFindResponse(
		String date,
		long count,
		int gradeNo
	) {
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
		this.daysBefore = LocalDate.now().compareTo(localDate);
		this.count = (int)count;
		this.gradeNo = gradeNo;
	}
}
