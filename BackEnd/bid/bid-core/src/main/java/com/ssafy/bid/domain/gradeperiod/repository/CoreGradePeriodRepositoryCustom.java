package com.ssafy.bid.domain.gradeperiod.repository;

import java.util.List;

import com.ssafy.bid.domain.grade.dto.GradePeriodsGetResponse;

public interface CoreGradePeriodRepositoryCustom {
	List<GradePeriodsGetResponse> findAllGradePeriodByGradeNo(int gradeNo);
}
