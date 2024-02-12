package com.ssafy.bid.domain.gradeperiod.service;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodListUpdateRequest;
import com.ssafy.bid.domain.user.UserType;

public interface GradePeriodService {
	void updateGradePeriod(UserType userType, int gradeNo, GradePeriodListUpdateRequest gradePeriodListUpdateRequest);
}
