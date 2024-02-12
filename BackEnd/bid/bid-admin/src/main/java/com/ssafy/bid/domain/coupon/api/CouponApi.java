package com.ssafy.bid.domain.coupon.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.ssafy.bid.domain.user.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CouponApi {

	private final CouponService couponService;

	@GetMapping("/{gradeNo}/coupons")
	public ResponseEntity<CouponListResponse> findCoupons(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo) {
		int userNo = userDetails.getUserInfo().getNo();
		return ResponseEntity.ok(couponService.findCoupons(gradeNo, userNo));
	}

	@PostMapping("/{gradeNo}/coupons")
	public ResponseEntity<?> addCoupon(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo,
		@RequestBody CouponCreateRequest couponCreateRequest) {
		int userNo = userDetails.getUserInfo().getNo();
		couponService.addCoupon(gradeNo, couponCreateRequest, userNo);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{gradeNo}/coupons/{couponNo}")
	public ResponseEntity<?> deleteCoupon(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo,
		@PathVariable int couponNo) {
		int userNo = userDetails.getUserInfo().getNo();
		couponService.deleteCoupon(couponNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gradeNo}/user-coupons")
	public ResponseEntity<List<UserCouponResponse>> findUserCoupons(
		@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int gradeNo) {
		int userNo = userDetails.getUserInfo().getNo();
		return ResponseEntity.ok(couponService.findUserCoupons(gradeNo, userNo));
	}

	@DeleteMapping("/{gradeNo}/user-coupons/{userCouponNo}")
	public ResponseEntity<?> acceptUserCoupon(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo, @PathVariable int userCouponNo) {
		int userNo = userDetails.getUserInfo().getNo();
		couponService.acceptUserCoupon(userCouponNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/user-coupons/{userCouponNo}")
	public ResponseEntity<?> rejectUserCoupon(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo, @PathVariable int userCouponNo) {
		int userNo = userDetails.getUserInfo().getNo();
		couponService.rejectUserCoupon(userCouponNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/coupons/{couponNo}/register")
	public ResponseEntity<?> registerCoupon(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo, @PathVariable int couponNo) {
		int userNo = userDetails.getUserInfo().getNo();
		couponService.registerCoupon(couponNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/coupons/{couponNo}/unregister")
	public ResponseEntity<?> unRegisterCoupon(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo, @PathVariable int couponNo) {
		int userNo = userDetails.getUserInfo().getNo();
		couponService.unRegisterCoupon(couponNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}
}
