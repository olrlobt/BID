package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.dto.StudentPasswordUpdateRequest;

public interface UserService {

	void checkAttendance(int userNo);

	boolean isAttendanceChecked(CustomUserInfo userInfo);

	void updatePassword(CustomUserInfo userInfo, StudentPasswordUpdateRequest request);
}
