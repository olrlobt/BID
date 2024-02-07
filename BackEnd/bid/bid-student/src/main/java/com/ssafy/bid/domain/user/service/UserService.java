package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.AttendanceResponse;
import com.ssafy.bid.domain.user.dto.PasswordResetRequest;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;

public interface UserService {

	void checkAttendance(int userNo);

	void resetPassword(String userId, PasswordResetRequest passwordResetRequest);

	AttendanceResponse getStudentAttendance(Integer userNo);
}
