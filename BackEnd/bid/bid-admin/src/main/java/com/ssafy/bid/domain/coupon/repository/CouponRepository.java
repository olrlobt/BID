package com.ssafy.bid.domain.coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	List<Coupon> findByGradeNo(int gradeNo);
}
