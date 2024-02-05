package com.ssafy.bid.domain.grade.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.grade.dto.AccountsStatisticsResponse;
import com.ssafy.bid.domain.grade.dto.BiddingsStatisticsResponse;
import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.GradePeriodsFindResponse;

public interface GradeRepositoryCustom {
	List<BiddingsStatisticsResponse> findBiddings(int gradeNo);

	List<GradePeriodsFindResponse> findGradePeriods(int gradeNo);

	Optional<GradeFindResponse> findGrade(int gradeNo);

	List<AccountsStatisticsResponse> findGradeStatistics(int gradeNo);
}
