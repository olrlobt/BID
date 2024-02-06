package com.ssafy.bid.domain.grade.repository;

import java.util.Optional;

import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;

public interface CoreGradeRepositoryCustom {
	Optional<GradeStatisticsFindResponse> findGradeStatisticsByGradeNo(int gradeNo);
}
