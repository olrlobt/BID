package com.ssafy.bid.domain.coupon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	List<Coupon> findByGradeNoAndCouponStatus(int gradeNo, CouponStatus couponStatus);
}
