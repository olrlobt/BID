package com.ssafy.bid.domain.coupon.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;
import com.ssafy.bid.domain.coupon.dto.CouponListResponse;
import com.ssafy.bid.domain.coupon.repository.CouponRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	@Transactional(readOnly = true)
	public CouponListResponse findCoupons(int gradeNo) {

		List<Coupon> registeredCoupons = couponRepository
			.findByGradeNoAndCouponStatus(gradeNo, CouponStatus.REGISTERED)
			.orElse(Collections.emptyList());

		List<Coupon> unregisteredCoupons = couponRepository
			.findByGradeNoAndCouponStatus(gradeNo, CouponStatus.UNREGISTERED)
			.orElse(Collections.emptyList());

		return new CouponListResponse(registeredCoupons, unregisteredCoupons);
	}


	public void acceptCoupon(int couponNo) {
		Coupon coupon = couponRepository.findById(couponNo)
			.orElseThrow(() -> new NoSuchElementException("쿠폰이 없습니다."));

		coupon.accept();
	}

	public void deleteCoupon(int couponNo) {
		if (!couponRepository.existsById(couponNo)) {
			throw new EntityNotFoundException("쿠폰이 없습니다.");
		}
		couponRepository.deleteById(couponNo);
	}

}
