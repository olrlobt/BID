package com.ssafy.bid.domain.gradeperiod.service;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodModifyRequest;

public interface GradePeriodService {
	void modifyGradePeriod(int gradeNo, GradePeriodModifyRequest gradePeriodModifyRequest);
}
