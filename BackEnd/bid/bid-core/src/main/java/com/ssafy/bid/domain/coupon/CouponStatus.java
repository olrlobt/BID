package com.ssafy.bid.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponStatus {
	UNREGISTERED("등록전"),
	REGISTERED("등록후");

	private final String couponStatus;

	private CouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
}
