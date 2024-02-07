package com.ssafy.bid.domain.gradeperiod.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradePeriodUpdateRequest {
	private int no;
	private LocalTime startPeriod;
	private LocalTime endPeriod;
}
