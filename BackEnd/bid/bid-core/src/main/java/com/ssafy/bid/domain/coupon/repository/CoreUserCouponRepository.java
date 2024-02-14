package com.ssafy.bid.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.coupon.UserCoupon;

public interface CoreUserCouponRepository extends JpaRepository<UserCoupon, Long> {
}
