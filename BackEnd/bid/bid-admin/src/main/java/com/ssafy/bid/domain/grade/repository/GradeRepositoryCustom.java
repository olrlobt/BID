package com.ssafy.bid.domain.grade.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.grade.dto.BiddingsFindResponse;
import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.GradePeriodsFindResponse;

public interface GradeRepositoryCustom {
	List<BiddingsFindResponse> findBiddings(int gradeNo);

	List<GradePeriodsFindResponse> findGradePeriods(int gradeNo);

	Optional<GradeFindResponse> findGrade(int gradeNo);
}
