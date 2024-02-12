package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.AdminPasswordUpdateRequest;
import com.ssafy.bid.domain.user.dto.AdminSaveRequest;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentSaveRequest;
import com.ssafy.bid.domain.user.dto.StudentsGetResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationCheckRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.UserDeleteRequest;
import com.ssafy.bid.domain.user.dto.UserIdFindRequest;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;

public interface UserService {
	void sendTelAuthentication(TelAuthenticationSendRequest telAuthenticationSendRequest);

	void sendRegisterTelAuthentication(TelAuthenticationSendRequest request);

	boolean checkTelAuthentication(TelAuthenticationCheckRequest request);

	boolean isDuplicated(String id);

	List<SchoolsFindResponse> getSchools(String name);

	void saveAdmin(AdminSaveRequest request);

	void saveStudent(UserType userType, StudentSaveRequest request);

	void resetStudentPassword(UserType userType, int userNo);

	void updateAdminPassword(AdminPasswordUpdateRequest adminPasswordUpdateRequest);

	List<StudentsGetResponse> getStudents(UserType userType, int gradeNo);

	String getUserId(UserIdFindRequest request);

	void updateUser(UserType userType, int userNo, UserUpdateRequest request);

	void deleteUser(UserType userType, int userNo, UserDeleteRequest request);

	List<BallsFindResponse> getAllBalls(UserType userType, int gradeNo);

	void resetAllBalls(UserType userType, int gradeNo);
}
