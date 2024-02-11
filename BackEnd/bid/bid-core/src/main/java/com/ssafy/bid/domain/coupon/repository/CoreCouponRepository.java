package com.ssafy.bid.domain.coupon.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;

import jakarta.validation.constraints.NotNull;

public interface CoreCouponRepository extends JpaRepository<Coupon, Integer> {

	List<Coupon> findAllByGradeNoAndCouponStatus(@NotNull Integer gradeNo, @NotNull CouponStatus couponStatus);
}
