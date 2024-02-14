package com.ssafy.bid.domain.gradeperiod.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

@Getter
public class GradePeriodUpdateRequest {
	private int no;
	private LocalTime startPeriod;
	private LocalTime endPeriod;

	public GradePeriodUpdateRequest(
		int no,
		String startPeriod,
		String endPeriod
	) {
		this.no = no;
		this.startPeriod = LocalTime.parse(startPeriod, DateTimeFormatter.ofPattern("HH:mm"));
		this.endPeriod = LocalTime.parse(endPeriod, DateTimeFormatter.ofPattern("HH:mm"));
	}
}
