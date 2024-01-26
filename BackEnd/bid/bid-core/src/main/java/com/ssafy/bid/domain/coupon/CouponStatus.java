package com.ssafy.bid.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponStatus {
	BEFORE_APPROVAL("승인전"),
	AFTER_APPROVAL("승인후");

	private final String couponStatus;

	private CouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
}
