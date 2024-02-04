package com.ssafy.bid.domain.coupon.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.coupon.UserCoupon;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, UserCouponCustomRepository {
}
