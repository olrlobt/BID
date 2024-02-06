package com.ssafy.bid.domain.grade.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.grade.dto.ExpenditureStatisticsFindResponse;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;
import com.ssafy.bid.domain.grade.dto.WinningBiddingStatisticsFindResponse;

public interface CoreGradeRepositoryCustom {
	Optional<GradeStatisticsFindResponse> findGradeStatisticsByGradeNo(int gradeNo);

	List<ExpenditureStatisticsFindResponse> findAllBiddingDealTypeStatistics();

	List<WinningBiddingStatisticsFindResponse> findAllWinningBiddingStatistics();
}
