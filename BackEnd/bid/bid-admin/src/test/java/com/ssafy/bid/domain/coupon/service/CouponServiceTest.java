package com.ssafy.bid.domain.coupon.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;
import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.coupon.UserCoupon;
import com.ssafy.bid.domain.coupon.dto.CouponCreateRequest;
import com.ssafy.bid.domain.coupon.dto.CouponListResponse;
import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;
import com.ssafy.bid.domain.coupon.repository.CouponRepository;
import com.ssafy.bid.domain.coupon.repository.UserCouponRepository;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

	@InjectMocks
	private CouponService couponService;

	@Mock
	private CouponRepository couponRepository;

	@Mock
	private UserCouponRepository userCouponRepository;

	private Coupon coupon;
	private UserCoupon userCoupon;

	@BeforeEach
	void setUp() {
		// 예시 Coupon 객체 초기화
		coupon = Coupon.builder()
			.no(1)
			.name("쿠폰")
			.description("설명")
			.couponStatus(CouponStatus.UNREGISTERED)
			.startPrice(100)
			.gradeNo(1)
			.build();
		// 예시 UserCoupon 객체 초기화
		userCoupon = new UserCoupon(1L, UsageStatus.REQUEST_PROGRESS, 1, 1, 1);
	}

	@Test
	void findCouponsTest_쿠폰_조회() {
		int gradeNo = 1;
		when(couponRepository.findByGradeNo(gradeNo)).thenReturn(Arrays.asList(coupon));

		CouponListResponse response = couponService.findCoupons(gradeNo);

		assertNotNull(response);
		assertEquals(1, response.getCoupons().size());
	}

	@Test
	void findUserCouponsTest_사용자_사용쿠폰_조회() {
		int gradeNo = 1;
		when(userCouponRepository.findUserCoupons(gradeNo)).thenReturn(
			Arrays.asList(new UserCouponResponse(1, 1, "제목", 1, "쿠폰명", 100)));

		List<UserCouponResponse> responses = couponService.findUserCoupons(gradeNo);

		assertNotNull(responses);
		assertFalse(responses.isEmpty());
	}

	@Test
	void addCouponTest_쿠폰_추가() {
		CouponCreateRequest request = CouponCreateRequest.builder().build();
		assertDoesNotThrow(() -> couponService.addCoupon(1, request));
	}

	@Test
	void deleteCouponTest_존재하는_쿠폰_삭제() {
		int couponNo = 1;
		int gradeNo = 1;
		when(couponRepository.existsById(couponNo)).thenReturn(true);

		assertDoesNotThrow(() -> couponService.deleteCoupon(couponNo, gradeNo));
		verify(couponRepository).deleteById(couponNo);
	}

	@Test
	void registerCouponTest_등록_거절_테스트() {
		int couponNo = 1;
		when(couponRepository.findByNoAndCouponStatus(couponNo, CouponStatus.UNREGISTERED)).thenReturn(
			Optional.of(coupon));

		assertDoesNotThrow(() -> couponService.registerCoupon(couponNo));
		assertEquals(coupon.getCouponStatus(), CouponStatus.REGISTERED);

		when(couponRepository.findByNoAndCouponStatus(couponNo, CouponStatus.REGISTERED)).thenReturn(
			Optional.of(coupon));

		assertDoesNotThrow(() -> couponService.unRegisterCoupon(couponNo));
		assertEquals(coupon.getCouponStatus(), CouponStatus.UNREGISTERED);
	}
}