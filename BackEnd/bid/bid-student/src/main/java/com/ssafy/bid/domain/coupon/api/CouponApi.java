package com.ssafy.bid.domain.coupon.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.service.CouponService;
import com.ssafy.bid.domain.user.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CouponApi {

	private final CouponService couponService;

	@GetMapping("/users/{userNo}/user-coupons")
	public ResponseEntity<List<UserCouponResponse>> findUserCoupons(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int userNo) {
		int gradeNo = userDetails.getUserInfo().getGradeNo();

		List<UserCouponResponse> userCoupons = couponService.findUserCoupons(userNo, gradeNo);
		return ResponseEntity.ok(userCoupons);
	}

	@PatchMapping("/users/{userNo}/user-coupons/{couponNo}")
	public ResponseEntity<List<UserCouponResponse>> useUserCoupon(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int userNo,
		@PathVariable int couponNo) {
		int userInfoNo = userDetails.getUserInfo().getNo();

		couponService.useUserCoupon(userNo, couponNo, userInfoNo);
		return ResponseEntity.noContent().build();
	}
}
