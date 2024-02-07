package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.RegisterRequest;
import com.ssafy.bid.domain.user.dto.SchoolResponse;
import com.ssafy.bid.domain.user.dto.StudentRegistrationRequest;
import com.ssafy.bid.domain.user.dto.StudentsFindResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.dto.UserWithdrawalRequest;

public interface UserService {
	public TelAuthenticationSendResponse sendTelAuthentication(
		TelAuthenticationSendRequest telAuthenticationSendRequest);

	List<StudentsFindResponse> findAllStudents(int gradeNo);

	List<SchoolResponse> searchSchools(String name);

	void registerUser(RegisterRequest request);

	boolean isIdDuplicate(String id);

	List<BallsFindResponse> findAllBalls(int gradeNo);

	void modifyBalls(int gradeNo);

	String findUserId(String name, String tel);

	void deleteUser(Integer userNo, UserWithdrawalRequest request);

	void updateUser(Integer userNo, UserUpdateRequest request);

	void registerStudent(StudentRegistrationRequest request);
}
