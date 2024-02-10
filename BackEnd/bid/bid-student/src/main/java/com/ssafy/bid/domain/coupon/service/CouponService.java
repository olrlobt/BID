package com.ssafy.bid.domain.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.coupon.UserCoupon;
import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.repository.UserCouponRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponService {

	private final UserCouponRepository userCouponRepository;

	@Transactional(readOnly = true)
	public List<UserCouponResponse> findUserCoupons(int userNo) {
		return userCouponRepository.findUserCoupons(userNo);
	}

	public void useUserCoupon(int userNo, int couponNo) {
		UserCoupon coupon = userCouponRepository.findFirstByUserNoAndCouponNoAndUseStatus(
			userNo, couponNo, UsageStatus.BEFORE_USE).orElseThrow(() -> new ResourceNotFoundException("사용할 쿠폰이 없습니다.", couponNo));

		coupon.request();
	}
}
