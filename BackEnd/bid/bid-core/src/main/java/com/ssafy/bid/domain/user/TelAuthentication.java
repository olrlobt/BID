package com.ssafy.bid.domain.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash(value = "code", timeToLive = 600)
public class TelAuthentication {

	@Id
	private String tel;

	private String code;

	private Boolean isAuthenticated;

	@Builder
	public TelAuthentication(
		String tel,
		String code,
		Boolean isAuthenticated
	) {
		this.tel = tel;
		this.code = code;
		this.isAuthenticated = isAuthenticated;
	}

	public void authenticate() {
		this.isAuthenticated = true;
	}
}
