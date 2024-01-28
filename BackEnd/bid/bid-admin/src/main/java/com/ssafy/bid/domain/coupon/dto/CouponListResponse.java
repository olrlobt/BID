package com.ssafy.bid.domain.coupon.dto;

import java.util.List;

import com.ssafy.bid.domain.coupon.Coupon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CouponListResponse {

	private List<Coupon> registeredCoupons;
	private List<Coupon> unregisteredCoupons;

}
