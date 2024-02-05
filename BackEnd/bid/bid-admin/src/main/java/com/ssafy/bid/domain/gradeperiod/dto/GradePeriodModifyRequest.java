package com.ssafy.bid.domain.gradeperiod.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradePeriodModifyRequest {
	List<GradePeriodsRequest> gradePeriodsRequests;
}
