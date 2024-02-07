package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.AttendanceResponse;
import com.ssafy.bid.domain.user.dto.PasswordResetRequest;

public interface UserService {

	void checkAttendance(int userNo);

	void resetPassword(String userId, PasswordResetRequest passwordResetRequest);

	AttendanceResponse getStudentAttendance(Integer userNo);
}
