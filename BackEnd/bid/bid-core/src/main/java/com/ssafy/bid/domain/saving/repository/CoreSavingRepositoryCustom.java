package com.ssafy.bid.domain.saving.repository;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingFindResponse;

public interface CoreSavingRepositoryCustom {
	List<SavingFindResponse> findAllByGradeNo(int gradeNo);
}
