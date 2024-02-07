package com.ssafy.bid.domain.saving.service;

import com.ssafy.bid.domain.saving.dto.SavingModifyRequest;

public interface SavingService {

	void modifySavings(int gradeNo, SavingModifyRequest savingModifyRequest);
}
