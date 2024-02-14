package com.ssafy.bid.domain.coupon.dto;

import lombok.Getter;

@Getter
public class UserCouponResponse {

	private int couponNo;
	private String couponName;
	private String couponDescription;
	private long count;

	public UserCouponResponse(int couponNo, String couponName, String couponDescription, long count) {
		this.couponNo = couponNo;
		this.couponName = couponName;
		this.couponDescription = couponDescription;
		this.count = count;
	}
}
