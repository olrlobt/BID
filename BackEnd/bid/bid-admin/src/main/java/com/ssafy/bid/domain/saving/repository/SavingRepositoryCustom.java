package com.ssafy.bid.domain.saving.repository;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingListGetResponse;

public interface SavingRepositoryCustom {
	List<SavingListGetResponse> findAllSavingsByGradeNo(int gradeNo);

	void deleteByGradeNo(int gradeNo);
}
