package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingFindResponse;

public interface CoreSavingService {
	List<SavingFindResponse> findAllSaving(int gradeNo);
}
