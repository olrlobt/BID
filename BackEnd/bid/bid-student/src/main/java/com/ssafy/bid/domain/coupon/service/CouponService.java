package com.ssafy.bid.domain.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.repository.UserCouponRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {

	private final UserCouponRepository userCouponRepository;

	public List<UserCouponResponse> findUserCoupons(int userNo) {
		return userCouponRepository.findUserCoupons(userNo);
	}

}
