package com.ssafy.bid.domain.user.dto;

import java.time.LocalDateTime;

import com.ssafy.bid.domain.user.TokenBlacklist;

public class TokenBlacklistDTO {
	private final String token;
	private final LocalDateTime expiryDate;

	public TokenBlacklistDTO(String token) {
		this.token = token;
		this.expiryDate = LocalDateTime.now().plusMinutes(30);
	}

	public TokenBlacklist toEntity() {
		return new TokenBlacklist(this.token, this.expiryDate);
	}
}
