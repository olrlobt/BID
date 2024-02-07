package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.AdminSaveRequest;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentSaveRequest;
import com.ssafy.bid.domain.user.dto.StudentsFindResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
import com.ssafy.bid.domain.user.dto.UserIdFindRequest;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.dto.UserDeleteRequest;

public interface UserService {
	TelAuthenticationSendResponse sendTelAuthentication(TelAuthenticationSendRequest telAuthenticationSendRequest);

	boolean isIdDuplicate(String id);

	List<SchoolsFindResponse> findSchools(String name);

	void saveAdmin(AdminSaveRequest request);

	void saveStudent(StudentSaveRequest request);

	List<StudentsFindResponse> findAllStudents(int gradeNo);

	String findUserId(UserIdFindRequest request);

	void updateUser(Integer userNo, UserUpdateRequest request);

	void deleteUser(Integer userNo, UserDeleteRequest request);

	List<BallsFindResponse> findAllBalls(int gradeNo);

	void resetAllBalls(int gradeNo);
}
