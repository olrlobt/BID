package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingPeriodModifyRequest {
	private LocalTime transferAlertPeriod;
	private LocalTime transferPeriod;
}
