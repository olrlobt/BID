package com.ssafy.bid.domain.grade.repository;

import java.util.List;

import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;

public interface GradeRepositoryCustom {
	List<GradeListGetResponse> findAllWithSchoolName();

	boolean existsByGradeNo(int gradeNo);
}
