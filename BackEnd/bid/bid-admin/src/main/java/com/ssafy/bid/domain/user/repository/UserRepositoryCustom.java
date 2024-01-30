package com.ssafy.bid.domain.user.repository;

import java.util.List;

import com.ssafy.bid.domain.user.dto.StudentsResponse;

public interface UserRepositoryCustom {
	List<StudentsResponse> findStudents(int gradeNo);
}
