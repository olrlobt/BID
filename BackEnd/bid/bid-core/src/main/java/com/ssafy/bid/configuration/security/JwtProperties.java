package com.ssafy.bid.configuration.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private final String secret;
	private final long expirationTime;
	private final long refreshExpirationTime;

	public JwtProperties(
		String secret,
		long expirationTime,
		long refreshExpirationTime
	) {
		this.secret = secret;
		this.expirationTime = expirationTime;
		this.refreshExpirationTime = refreshExpirationTime;
	}
}
