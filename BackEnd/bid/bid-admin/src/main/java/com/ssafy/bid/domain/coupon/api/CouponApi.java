package com.ssafy.bid.domain.coupon.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.coupon.dto.CouponListResponse;
import com.ssafy.bid.domain.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponApi {

	private final CouponService couponService;

	@GetMapping("/{gradeNo}/coupons")
	public ResponseEntity<CouponListResponse> findCoupons(@PathVariable int gradeNo){

		CouponListResponse coupons = couponService.findCoupons(gradeNo);

		if (coupons != null) {
			return ResponseEntity.ok(coupons);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
