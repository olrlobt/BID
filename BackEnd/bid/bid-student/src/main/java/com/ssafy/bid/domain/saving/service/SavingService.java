package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingExpireResponse;
import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;

public interface SavingService {
	List<SavingsResponse> findSavings(int gradeNo);

	void saveSaving(int userNo, SavingRequest savingRequest);

	void deleteSaving(int userNo, int savingNo);

	void transfer();

	List<SavingExpireResponse> expire();
}
