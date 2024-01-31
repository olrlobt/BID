package com.ssafy.bid.domain.gradeperiod.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradePeriodModifyRequest {
	List<GradePeriodsRequest> gradePeriodsRequests;
}
