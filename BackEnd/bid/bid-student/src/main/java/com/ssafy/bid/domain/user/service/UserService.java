package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.CustomUserInfo;

public interface UserService {

	void checkAttendance(int userNo);

	boolean isAttendanceChecked(CustomUserInfo userInfo);
}
