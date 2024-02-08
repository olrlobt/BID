package com.ssafy.bid.domain.grade.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;
import com.ssafy.bid.domain.user.Admin;

public interface GradeRepositoryCustom {
	List<GradeListGetResponse> findAllWithSchoolName(int userNo);

	boolean existsByGradeNo(int gradeNo);

	Optional<Admin> findAdminByUserNo(int userNo);
}
