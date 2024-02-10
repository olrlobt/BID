package com.ssafy.bid.domain.coupon.repository;

import java.util.List;

import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;

public interface UserCouponCustomRepository {
	List<UserCouponResponse> findUserCoupons(int userNo);
}
