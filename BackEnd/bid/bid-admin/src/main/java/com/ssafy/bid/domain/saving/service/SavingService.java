package com.ssafy.bid.domain.saving.service;

import com.ssafy.bid.domain.saving.dto.SavingListUpdateRequest;

public interface SavingService {
	void updateSaving(int gradeNo, SavingListUpdateRequest savingListUpdateRequest);
}
