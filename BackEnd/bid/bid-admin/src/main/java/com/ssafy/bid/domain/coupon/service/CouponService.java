package com.ssafy.bid.domain.coupon.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;
import com.ssafy.bid.domain.coupon.UsageStatus;
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
		// admin의 gradeNO 이 gradeNo과 다르면 error

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
		// admin의 gradeNO 이 gradeNo과 다르면 error

		if (!userCouponRepository.existsById(userCouponNo)) {
			throw new ResourceNotFoundException("사용할 쿠폰이 없습니다.", userCouponNo);
		}
		// Todo. 해당 학생 알림 주기
		userCouponRepository.deleteByNoAndUseStatus(userCouponNo, UsageStatus.REQUEST_PROGRESS);
	}

	public void rejectUserCoupon(long userCouponNo) {
		// admin의 gradeNO 이 gradeNo과 다르면 error

		UserCoupon userCoupon = userCouponRepository.findByNoAndUseStatus(userCouponNo, UsageStatus.REQUEST_PROGRESS)
			.orElseThrow(() -> new ResourceNotFoundException("사용할 쿠폰이 없습니다.", userCouponNo));

		// Todo. 해당 학생 알림 주기
		userCoupon.reject();
	}

	public void registerCoupon(int couponNo) {
		// admin의 gradeNO 이 gradeNo과 다르면 error

		Coupon coupon = couponRepository.findByNoAndCouponStatus(couponNo,
			CouponStatus.UNREGISTERED)
			.orElseThrow(() -> new ResourceNotFoundException("쿠폰이 없습니다."));

		coupon.register();
	}

	public void unRegisterCoupon(int couponNo) {
		// admin의 gradeNO 이 gradeNo과 다르면 error

		Coupon coupon = couponRepository.findByNoAndCouponStatus(couponNo,
				CouponStatus.REGISTERED)
			.orElseThrow(() -> new ResourceNotFoundException("쿠폰이 없습니다."));

		coupon.unRegister();
	}
}
