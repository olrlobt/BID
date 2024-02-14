package com.ssafy.bid.configuration.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {
	private final String host;
	private final int port;

	public RedisProperties(
		String host,
		int port
	) {
		this.host = host;
		this.port = port;
	}
}
