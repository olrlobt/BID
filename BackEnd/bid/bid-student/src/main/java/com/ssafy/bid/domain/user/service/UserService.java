package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.AttendanceResponse;

public interface UserService {

	void checkAttendance(int userNo);

	AttendanceResponse getStudentAttendance(Integer userNo);
}
