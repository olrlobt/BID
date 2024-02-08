package com.ssafy.bid.domain.gradeperiod.service;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodListUpdateRequest;

public interface GradePeriodService {
	void updateGradePeriod(int gradeNo, GradePeriodListUpdateRequest gradePeriodListUpdateRequest);
}
