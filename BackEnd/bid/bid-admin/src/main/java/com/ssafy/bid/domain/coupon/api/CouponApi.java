package com.ssafy.bid.domain.coupon.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.coupon.dto.CouponCreateRequest;
import com.ssafy.bid.domain.coupon.dto.CouponListResponse;
import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CouponApi {

	private final CouponService couponService;

	@GetMapping("/{gradeNo}/coupons")
	public ResponseEntity<CouponListResponse> findCoupons(@PathVariable int gradeNo) {
		return ResponseEntity.ok(couponService.findCoupons(gradeNo));
	}

	@PostMapping("/{gradeNo}/coupons")
	public ResponseEntity<?> addCoupon(@PathVariable int gradeNo,
		@RequestBody CouponCreateRequest couponCreateRequest) {
		couponService.addCoupon(gradeNo, couponCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/{gradeNo}/user-coupons")
	public ResponseEntity<List<UserCouponResponse>> findUserCoupons(@PathVariable int gradeNo) {
		return ResponseEntity.ok(couponService.findUserCoupons(gradeNo));
	}

	@DeleteMapping("/{gradeNo}/user-coupons/{userCouponNo}")
	public ResponseEntity<?> acceptUserCoupon(@PathVariable int gradeNo, @PathVariable int userCouponNo) {
		couponService.acceptUserCoupon(userCouponNo);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/user-coupons/{userCouponNo}")
	public ResponseEntity<?> rejectUserCoupon(@PathVariable int gradeNo, @PathVariable int userCouponNo) {
		couponService.rejectUserCoupon(userCouponNo);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/coupons/{couponNo}/register")
	public ResponseEntity<?> registerCoupon(@PathVariable int gradeNo, @PathVariable int couponNo) {
		couponService.registerCoupon(couponNo);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/coupons/{couponNo}/unregister")
	public ResponseEntity<?> unRegisterCoupon(@PathVariable int gradeNo, @PathVariable int couponNo) {
		couponService.unRegisterCoupon(couponNo);
		return ResponseEntity.noContent().build();
	}
}
