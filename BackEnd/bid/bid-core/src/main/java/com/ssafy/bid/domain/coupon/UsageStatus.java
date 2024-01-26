package com.ssafy.bid.domain.coupon;

import lombok.Getter;

@Getter
public enum UsageStatus {
	BEFORE_USE("사용전"),
	REQUEST_PROGRESS("승인요청중");

	private final String usageStatus;

	private UsageStatus(String usageStatus) {
		this.usageStatus = usageStatus;
	}
}
