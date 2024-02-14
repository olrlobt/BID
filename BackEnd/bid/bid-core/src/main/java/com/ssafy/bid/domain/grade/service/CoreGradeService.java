package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeStatisticsGetResponse;

public interface CoreGradeService {
	GradeStatisticsGetResponse getGradeStatistics(int gradeNo);

	void updateGradeStatistics();

	void recommendSalary();
}
