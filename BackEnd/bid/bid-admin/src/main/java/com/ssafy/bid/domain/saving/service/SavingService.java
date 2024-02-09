package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingListGetResponse;
import com.ssafy.bid.domain.saving.dto.SavingListUpdateRequest;

public interface SavingService {
	List<SavingListGetResponse> getAllSaving(int gradeNo);

	void updateSaving(int gradeNo, SavingListUpdateRequest savingListUpdateRequest);
}
