package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public class SavingPeriodUpdateRequest {
	private LocalTime transferAlertPeriod;
	private LocalTime transferPeriod;
}
