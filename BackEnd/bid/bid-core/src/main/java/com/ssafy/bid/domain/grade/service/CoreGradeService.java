package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeStatisticsGetResponse;

public interface CoreGradeService {
	GradeStatisticsGetResponse findGradeStatistics(int gradeNo);

	void updateGradeStatistics();
}
