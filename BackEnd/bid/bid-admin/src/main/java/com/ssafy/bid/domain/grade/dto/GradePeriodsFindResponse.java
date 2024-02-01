package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradePeriodsFindResponse {
	private int no;
	private int sequence;
	private LocalTime startPeriod;
	private LocalTime endPeriod;

	public GradePeriodsFindResponse(
		int no,
		int sequence,
		LocalTime startPeriod,
		LocalTime endPeriod
	) {
		this.no = no;
		this.sequence = sequence;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}
}
