package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;

public interface UserService {
	List<StudentsResponse> findStudents(int gradeNo);
	StudentResponse findStudent(int userNo, StudentRequest studentRequest);
}
