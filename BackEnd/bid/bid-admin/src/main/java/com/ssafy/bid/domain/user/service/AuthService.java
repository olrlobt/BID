package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.LoginRequest;

public interface AuthService {
    String login(LoginRequest request);
}
