package com.ssafy.bid.domain.grade.repository;

import java.util.List;

import com.ssafy.bid.domain.grade.dto.GradePeriodsFindResponse;

public interface CoreGradePeriodRepositoryCustom {
	List<GradePeriodsFindResponse> findAllGradePeriodByGradeNo(int gradeNo);
}
