package com.ssafy.bid.domain.coupon.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

	@DeleteMapping("/{gradeNo}/coupons/requests/{userCouponNo}")
	public ResponseEntity<?> acceptCouponRequest(@PathVariable int gradeNo, @PathVariable int userCouponNo) {
		couponService.acceptCouponRequest(userCouponNo);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/coupons/requests/{userCouponNo}")
	public ResponseEntity<?> rejectCouponRequest(@PathVariable int gradeNo, @PathVariable int userCouponNo) {
		couponService.rejectCouponRequest(userCouponNo);
		return ResponseEntity.noContent().build();
	}
}
