package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingModifyRequest;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;

public interface SavingService {
	List<SavingsResponse> findSavings(int gradeNo);

	void modifySavings(int gradeNo, SavingModifyRequest savingModifyRequest);
}
