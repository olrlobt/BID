package com.ssafy.bid.configuration.message;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "cool-sms")
public class CoolSMSProperties {

	private final String apiKey;
	private final String apiSecret;
	private final String fromNumber;
	private final String domain;

	public CoolSMSProperties(
		String apiKey,
		String apiSecret,
		String fromNumber,
		String domain
	) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.fromNumber = fromNumber;
		this.domain = domain;
	}
}
