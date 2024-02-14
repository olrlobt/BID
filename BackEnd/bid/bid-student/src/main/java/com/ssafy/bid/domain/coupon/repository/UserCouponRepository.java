package com.ssafy.bid.domain.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.coupon.UserCoupon;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, UserCouponCustomRepository {

	Optional<UserCoupon> findFirstByUserNoAndCouponNoAndUseStatus(Integer userNo, Integer couponNo,
		UsageStatus useStatus);
}
