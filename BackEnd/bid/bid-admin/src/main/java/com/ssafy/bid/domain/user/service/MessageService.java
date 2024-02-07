package com.ssafy.bid.domain.user.service;

public interface MessageService {
	void sendAuthenticationCode(String to, String code);
}
