package com.ssafy.bid.domain.saving.service;

import com.ssafy.bid.domain.saving.dto.SavingModifyRequest;

public interface SavingService {
	void modifySaving(int gradeNo, SavingModifyRequest savingModifyRequest);
}
