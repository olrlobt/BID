package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.dto.*;
import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.BallsResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;

public interface UserService {
	List<StudentsResponse> findStudents(int gradeNo);

	StudentResponse findStudent(int userNo, StudentRequest studentRequest);

	List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest);

	List<School> searchSchools(String name);

	void registerUser(RegisterRequest request) throws Exception;

	boolean isIdDuplicate(String id);

	List<BallsResponse> findBalls(int gradeNo);

	void modifyBalls(int gradeNo);
}
