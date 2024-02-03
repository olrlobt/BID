package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.dto.*;

public interface UserService {
	List<StudentsResponse> findStudents(int gradeNo);

	StudentResponse findStudent(int userNo, StudentRequest studentRequest);

	List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest);

	List<School> searchSchools(String name);

	void registerUser(RegisterRequest request) throws Exception;

	boolean isIdDuplicate(String id);

}
