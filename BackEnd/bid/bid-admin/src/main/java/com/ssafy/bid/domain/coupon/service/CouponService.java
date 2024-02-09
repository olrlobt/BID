package com.ssafy.bid.domain.coupon.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.UserCoupon;
import com.ssafy.bid.domain.coupon.dto.CouponCreateRequest;
import com.ssafy.bid.domain.coupon.dto.CouponListResponse;
import com.ssafy.bid.domain.coupon.dto.CouponResponse;
import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.repository.CouponRepository;
import com.ssafy.bid.domain.coupon.repository.UserCouponRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;
	private final UserCouponRepository userCouponRepository;

	@Transactional(readOnly = true)
	public CouponListResponse findCoupons(int gradeNo) {
		// admin의 gradeNO 이 gradeNo과 다르면 error

		return new CouponListResponse(couponRepository
			.findByGradeNo(gradeNo)
			.stream()
			.map(CouponResponse::to)
			.toList());
	}

	@Transactional(readOnly = true)
	public List<UserCouponResponse> findUserCoupons(int gradeNo) {
		return userCouponRepository.findUserCoupons(gradeNo);
	}

	public void addCoupon(int gradeNo, CouponCreateRequest couponCreateRequest) {
		// admin의 gradeNO 이 gradeNo과 다르면 error

		couponCreateRequest.setGradeNo(gradeNo);
		couponRepository.save(couponCreateRequest.toEntity());
	}

	public void deleteCoupon(int couponNo, int gradeNo) {
		// admin의 gradeNO 이 gradeNo과 다르면 error

		if (!couponRepository.existsById(couponNo)) {
			throw new ResourceNotFoundException("쿠폰이 없습니다.", couponNo);
		}
		couponRepository.deleteById(couponNo);
	}

	public void acceptUserCoupon(long userCouponNo) {
		if (!userCouponRepository.existsById(userCouponNo)) {
			throw new EntityNotFoundException("쿠폰이 없습니다.");
		}
		// Todo. 해당 학생 알림 주기
		userCouponRepository.deleteById(userCouponNo);
	}

	public void rejectUserCoupon(long userCouponNo) {
		UserCoupon userCoupon = userCouponRepository.findById(userCouponNo)
			.orElseThrow(() -> new NoSuchElementException("쿠폰이 없습니다."));

		userCoupon.reject();
	}

	public void registerCoupon(int couponNo) {
		Coupon coupon = couponRepository.findById(couponNo)
			.orElseThrow(() -> new NoSuchElementException("쿠폰이 없습니다."));

		coupon.register();
	}

	public void unRegisterCoupon(int couponNo) {
		Coupon coupon = couponRepository.findById(couponNo)
			.orElseThrow(() -> new NoSuchElementException("쿠폰이 없습니다."));

		coupon.unRegister();
	}
}
