package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradePeriodsFindResponse {
	private int no;
	private int sequence;
	private String startPeriod;
	private String endPeriod;

	public GradePeriodsFindResponse(
		int no,
		int sequence,
		LocalTime startPeriod,
		LocalTime endPeriod
	) {
		this.no = no;
		this.sequence = sequence;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		this.startPeriod = startPeriod.format(formatter);
		this.endPeriod = endPeriod.format(formatter);
	}
}
