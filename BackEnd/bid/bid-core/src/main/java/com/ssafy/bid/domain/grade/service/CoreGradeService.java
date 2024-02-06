package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;

public interface CoreGradeService {
	GradeStatisticsFindResponse findGradeStatistics(int gradeNo);
}
