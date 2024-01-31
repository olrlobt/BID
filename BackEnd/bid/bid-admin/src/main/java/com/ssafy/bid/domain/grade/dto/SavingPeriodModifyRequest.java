package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SavingPeriodModifyRequest {
	private LocalTime transferAlertPeriod;
	private LocalTime transferPeriod;
}
