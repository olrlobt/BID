package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.StudentsResponse;

public interface UserService {
	List<StudentsResponse> searchStudents(int gradeNo);
}
