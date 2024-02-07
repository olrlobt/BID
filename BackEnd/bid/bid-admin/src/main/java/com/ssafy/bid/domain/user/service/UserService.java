package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.BallsResponse;
import com.ssafy.bid.domain.user.dto.RegisterRequest;
import com.ssafy.bid.domain.user.dto.SchoolResponse;
import com.ssafy.bid.domain.user.dto.StudentRegistrationRequest;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.dto.UserWithdrawalRequest;

public interface UserService {
	public TelAuthenticationSendResponse sendTelAuthentication(
		TelAuthenticationSendRequest telAuthenticationSendRequest);

	List<StudentsResponse> findStudents(int gradeNo);

	StudentResponse findStudent(int userNo, StudentRequest studentRequest);

	List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest);

	List<SchoolResponse> searchSchools(String name);

	void registerUser(RegisterRequest request);

	boolean isIdDuplicate(String id);

	List<BallsResponse> findBalls(int gradeNo);

	void modifyBalls(int gradeNo);

	String findUserId(String name, String tel);

	void deleteUser(Integer userNo, UserWithdrawalRequest request);

	void updateUser(Integer userNo, UserUpdateRequest request);

	void registerStudent(StudentRegistrationRequest request);
}
