package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingListGetResponse;
import com.ssafy.bid.domain.saving.dto.SavingListUpdateRequest;
import com.ssafy.bid.domain.user.UserType;

public interface SavingService {
	List<SavingListGetResponse> getAllSaving(UserType userType, int gradeNo);

	void updateSaving(UserType userType, int gradeNo, SavingListUpdateRequest savingListUpdateRequest);
}
