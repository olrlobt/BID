package com.ssafy.bid.domain.coupon.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CouponListResponse {

	private List<CouponResponse> registeredCoupons;
	private List<CouponResponse> unregisteredCoupons;
}
