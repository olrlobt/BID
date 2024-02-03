package com.ssafy.bid.domain.coupon.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.coupon.dto.CouponListResponse;
import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponApi {

	private final CouponService couponService;

	@GetMapping("/{gradeNo}/coupons")
	public ResponseEntity<CouponListResponse> findCoupons(@PathVariable int gradeNo) {
		return ResponseEntity.ok(couponService.findCoupons(gradeNo));
	}

	@GetMapping("/{gradeNo}/coupons/requests")
	public ResponseEntity<List<UserCouponResponse>> findCouponRequests(@PathVariable int gradeNo) {
		return ResponseEntity.ok(couponService.findCouponRequests(gradeNo));
	}


	@PatchMapping("/{gradeNo}/coupons/{couponNo}")
	public void acceptCoupon(@PathVariable int gradeNo, @PathVariable int couponNo) {
		couponService.acceptCoupon(couponNo);
	}

	@DeleteMapping("/{gradeNo}/coupons/{couponNo}")
	public void deleteCoupon(@PathVariable int gradeNo, @PathVariable int couponNo) {
		couponService.deleteCoupon(couponNo);
	}
}
