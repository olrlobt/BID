package com.ssafy.bid.domain.grade.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.grade.dto.ExpenditureStatisticsGetResponse;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsGetResponse;
import com.ssafy.bid.domain.grade.dto.WinningBiddingStatisticsGetResponse;

public interface CoreGradeRepositoryCustom {
	Optional<GradeStatisticsGetResponse> findGradeStatisticsByGradeNo(int gradeNo);

	List<ExpenditureStatisticsGetResponse> findAllBiddingDealTypeStatistics();

	List<WinningBiddingStatisticsGetResponse> findAllWinningBiddingStatistics();
}
