package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.LoginRequest;

public interface CoreUserService {
	String login(LoginRequest loginRequest);

	void logout(String token);
}
