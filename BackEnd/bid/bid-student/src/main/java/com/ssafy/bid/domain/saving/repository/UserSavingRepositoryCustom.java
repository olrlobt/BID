package com.ssafy.bid.domain.saving.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.saving.dto.TaxRateResponse;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;

public interface UserSavingRepositoryCustom {
	List<UserSavingListGetResponse> findAllByUserNoAndGradeNo(int userNo, int gradeNo);

	Optional<TaxRateResponse> findAllBiddingIncomes(int gradeNo, int userNo);
}
