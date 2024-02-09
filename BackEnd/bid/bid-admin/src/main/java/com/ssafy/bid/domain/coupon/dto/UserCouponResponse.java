package com.ssafy.bid.domain.coupon.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCouponResponse {

	private long no;
	private int userNo;
	private String userName;
	private int couponNo;
	private String couponName;
}
