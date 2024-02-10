package com.ssafy.bid.domain.coupon.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CouponApi {

	private final CouponService couponService;

	@GetMapping("/users/{userNo}/user-coupons")
	public ResponseEntity<List<UserCouponResponse>> findUserCoupons(@PathVariable int userNo) {
		List<UserCouponResponse> userCoupons = couponService.findUserCoupons(userNo);
		return ResponseEntity.ok(userCoupons);
	}

	@PatchMapping("/users/{userNo}/user-coupons/{couponNo}")
	public ResponseEntity<List<UserCouponResponse>> useUserCoupon(@PathVariable int userNo,
		@PathVariable int couponNo) {

		couponService.useUserCoupon(userNo, couponNo);
		return ResponseEntity.noContent().build();
	}
}
