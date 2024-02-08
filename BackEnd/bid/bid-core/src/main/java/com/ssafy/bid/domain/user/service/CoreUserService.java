package com.ssafy.bid.domain.user.service;

import java.util.List;

import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;

public interface CoreUserService {
	String login(LoginRequest loginRequest);

	void logout(String token);

	StudentFindResponse findStudent(int userNo, StudentFindRequest studentFindRequest);

	List<AccountFindResponse> findAccount(int userNo, AccountFindRequest accountFindRequest);
}
