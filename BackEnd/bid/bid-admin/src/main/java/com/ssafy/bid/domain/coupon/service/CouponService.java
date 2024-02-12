package com.ssafy.bid.domain.coupon.service;

import java.util.List;

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
import com.ssafy.bid.domain.grade.dto.GradeProjection;
import com.ssafy.bid.domain.grade.repository.GradeRepository;
import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;
	private final UserCouponRepository userCouponRepository;
	private final NotificationService notificationService;
	private final GradeRepository gradeRepository;

	@Transactional(readOnly = true)
	public CouponListResponse findCoupons(int gradeNo, int userNo) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}
		return new CouponListResponse(couponRepository
			.findByGradeNo(gradeNo)
			.stream()
			.map(CouponResponse::to)
			.toList());
	}

	@Transactional(readOnly = true)
	public List<UserCouponResponse> findUserCoupons(int gradeNo, int userNo) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}
		return userCouponRepository.findUserCoupons(gradeNo);
	}

	public void addCoupon(int gradeNo, CouponCreateRequest couponCreateRequest, int userNo, UserType userType) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		couponCreateRequest.setGradeNo(gradeNo);
		couponRepository.save(couponCreateRequest.toEntity());
	}

	public void deleteCoupon(int couponNo, int gradeNo, int userNo, UserType userType) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		} else if (!couponRepository.existsById(couponNo)) {
			throw new ResourceNotFoundException("쿠폰이 없습니다.", couponNo);
		}
		couponRepository.deleteById(couponNo);
	}

	public void acceptUserCoupon(long userCouponNo, int gradeNo, int userNo, UserType userType) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		UserCoupon userCoupon = userCouponRepository.findById(userCouponNo)
			.orElseThrow(() -> new ResourceNotFoundException("사용할 쿠폰이 없습니다.", userCouponNo));

		NotificationRequest notificationRequest = NotificationRequest.builder()
			.title("쿠폰 사용")
			.content("쿠폰 사용이 승인되었어요")
			.receiverNo(userCoupon.getUserNo())
			.notificationType(NotificationType.COUPON_APPROVE)
			.build();
		notificationService.send(notificationRequest);

		userCouponRepository.deleteByNoAndUseStatus(userCouponNo, UsageStatus.REQUEST_PROGRESS);
	}

	public void rejectUserCoupon(long userCouponNo, int gradeNo, int userNo, UserType userType) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		UserCoupon userCoupon = userCouponRepository.findByNoAndUseStatus(userCouponNo, UsageStatus.REQUEST_PROGRESS)
			.orElseThrow(() -> new ResourceNotFoundException("사용할 쿠폰이 없습니다.", userCouponNo));

		NotificationRequest notificationRequest = NotificationRequest.builder()
			.title("쿠폰 거절")
			.content("쿠폰 사용이 거절되었어요")
			.receiverNo(userCoupon.getUserNo())
			.notificationType(NotificationType.COUPON_APPROVE)
			.build();
		notificationService.send(notificationRequest);

		userCoupon.reject();
	}

	public void registerCoupon(int couponNo, int gradeNo, int userNo, UserType userType) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		Coupon coupon = couponRepository.findByNoAndCouponStatus(couponNo,
				CouponStatus.UNREGISTERED)
			.orElseThrow(() -> new ResourceNotFoundException("쿠폰이 없습니다."));

		coupon.register();
	}

	public void unRegisterCoupon(int couponNo, int gradeNo, int userNo, UserType userType) {
		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		Coupon coupon = couponRepository.findByNoAndCouponStatus(couponNo,
				CouponStatus.REGISTERED)
			.orElseThrow(() -> new ResourceNotFoundException("쿠폰이 없습니다."));

		coupon.unRegister();
	}
}
