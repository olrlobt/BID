package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.LoginResponse;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface CoreUserService {
	LoginResponse login(LoginRequest loginRequest);

	void logout(int userNo, HttpServletRequest request);

	StudentFindResponse findStudent(int userNo, StudentFindRequest studentFindRequest);

	List<AccountFindResponse> findAccount(int userNo, AccountFindRequest accountFindRequest);

	void resetAttendance();

	void calculateIncomeLevel();
}
