package com.ssafy.bid.domain.grade.dto;

import com.ssafy.bid.domain.grade.Grade;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeBiddingAvgPriceResponse {
	private Grade grade;
	private int avgBeforeFourWeeks;
	private int avgBeforeTwoWeeks;
	private int diff;

	public GradeBiddingAvgPriceResponse(
		Grade grade,
		double avgBeforeFourWeeks,
		double avgBeforeTwoWeeks
	) {
		this.grade = grade;
		this.avgBeforeFourWeeks = (int)avgBeforeFourWeeks;
		this.avgBeforeTwoWeeks = (int)avgBeforeTwoWeeks;
		this.diff =
			avgBeforeFourWeeks == 0 ? 0 : (int)((avgBeforeFourWeeks - avgBeforeTwoWeeks) / avgBeforeFourWeeks) * 100;
	}
}
