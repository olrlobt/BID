package com.ssafy.bid.domain.saving.repository;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingsResponse;

public interface SavingRepositoryCustom {
	List<SavingsResponse> findSavings(int gradeNo);
}
